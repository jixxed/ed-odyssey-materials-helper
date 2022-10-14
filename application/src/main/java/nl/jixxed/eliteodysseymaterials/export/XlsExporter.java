package nl.jixxed.eliteodysseymaterials.export;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.enums.*;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.StorageService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class XlsExporter {

    public static XSSFWorkbook createXlsWishlist(final Map<Data, Integer> wishlistNeededDatas, final Map<Good, Integer> wishlistNeededGoods, final Map<Asset, Integer> wishlistNeededAssets) {
        final XSSFWorkbook workbook = new XSSFWorkbook();
        final XSSFSheet sheet = workbook.createSheet("wishlist");
//        final StringBuilder textBuilder = new StringBuilder();
        final Row row = sheet.createRow(0);
        final CellStyle style = workbook.createCellStyle();
        final XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
        createCell(sheet, row, 0, "Material", style);
        createCell(sheet, row, 1, "Available BP+S", style);
        createCell(sheet, row, 2, "Available FC", style);
        createCell(sheet, row, 3, "Available Total", style);
        createCell(sheet, row, 4, "Required", style);
        createCell(sheet, row, 5, "Need", style);
        final AtomicInteger rowNumber = new AtomicInteger(1);
        final CellStyle dataStyle = workbook.createCellStyle();
        final XSSFFont dataFont = workbook.createFont();
        dataFont.setFontHeight(14);
        dataStyle.setFont(dataFont);
        List.of(wishlistNeededDatas, wishlistNeededGoods, wishlistNeededAssets).forEach(wishlistNeededMaterials ->
                wishlistNeededMaterials.entrySet().stream()
                        .sorted(Comparator.comparing(item -> item.getKey().getStorageType()))
                        .forEach(item -> {
                            final String materialName = LocaleService.getLocalizedStringForCurrentLocale(item.getKey().getLocalizationKey());
                            final Integer ship = switch (item.getKey().getStorageType()) {
                                case GOOD -> StorageService.getGoods().get(item.getKey()).getAvailableValue();
                                case DATA -> StorageService.getData().get(item.getKey()).getAvailableValue();
                                case ASSET -> StorageService.getAssets().get(item.getKey()).getAvailableValue();
                                case TRADE, CONSUMABLE, OTHER -> 0;
                            };
                            final Integer fc = switch (item.getKey().getStorageType()) {
                                case GOOD -> StorageService.getGoods().get(item.getKey()).getFleetCarrierValue();
                                case DATA -> StorageService.getData().get(item.getKey()).getFleetCarrierValue();
                                case ASSET -> StorageService.getAssets().get(item.getKey()).getFleetCarrierValue();
                                case TRADE, CONSUMABLE, OTHER -> 0;
                            };
                            final Integer total = switch (item.getKey().getStorageType()) {
                                case GOOD -> StorageService.getGoods().get(item.getKey()).getTotalValue();
                                case DATA -> StorageService.getData().get(item.getKey()).getTotalValue();
                                case ASSET -> StorageService.getAssets().get(item.getKey()).getTotalValue();
                                case TRADE, CONSUMABLE, OTHER -> 0;
                            };
                            final Row dataRow = sheet.createRow(rowNumber.getAndIncrement());
                            createCell(sheet, dataRow, 0, materialName, dataStyle);
                            createCell(sheet, dataRow, 1, ship, dataStyle);
                            createCell(sheet, dataRow, 2, fc, dataStyle);
                            createCell(sheet, dataRow, 3, total, dataStyle);
                            createCell(sheet, dataRow, 4, item.getValue(), dataStyle);
                            createCell(sheet, dataRow, 5, Math.max(0, item.getValue() - ship), dataStyle);
                        })
        );
        return workbook;
    }

    public static XSSFWorkbook createXlsWishlist(final Map<Raw, Integer> wishlistNeededRaw, final Map<Encoded, Integer> wishlistNeededEncoded, final Map<Manufactured, Integer> wishlistNeededManufactured, final Map<Commodity, Integer> wishlistNeededCommodity) {
        final XSSFWorkbook workbook = new XSSFWorkbook();
        final XSSFSheet sheet = workbook.createSheet("wishlist");
//        final StringBuilder textBuilder = new StringBuilder();
        final Row row = sheet.createRow(0);
        final CellStyle style = workbook.createCellStyle();
        final XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
        createCell(sheet, row, 0, "Material", style);
        createCell(sheet, row, 1, "Available S", style);
        createCell(sheet, row, 2, "Available FC", style);
        createCell(sheet, row, 3, "Available Total", style);
        createCell(sheet, row, 4, "Required", style);
        createCell(sheet, row, 5, "Need", style);
        final AtomicInteger rowNumber = new AtomicInteger(1);
        final CellStyle dataStyle = workbook.createCellStyle();
        final XSSFFont dataFont = workbook.createFont();
        dataFont.setFontHeight(14);
        dataStyle.setFont(dataFont);
        ((List<Map<HorizonsMaterial, Integer>>) (List<?>) List.of(wishlistNeededRaw, wishlistNeededEncoded, wishlistNeededManufactured, wishlistNeededCommodity)).forEach(wishlistNeededMaterials ->
                wishlistNeededMaterials.entrySet().stream()
                        .sorted(Comparator.comparing(item -> item.getKey().getStorageType()))
                        .forEach(item -> {
                            final String materialName = LocaleService.getLocalizedStringForCurrentLocale(item.getKey().getLocalizationKey());
                            final Integer ship = switch (item.getKey().getStorageType()) {
                                case RAW, ENCODED, MANUFACTURED -> StorageService.getMaterialCount(item.getKey());
                                case COMMODITY ->
                                        StorageService.getCommodityCount((Commodity) item.getKey(), StoragePool.SHIP);
                                default -> 0;
                            };
                            final Integer fc = item.getKey() instanceof Commodity commodity ? StorageService.getCommodityCount(commodity, StoragePool.FLEETCARRIER) : 0;
                            final Integer total = switch (item.getKey().getStorageType()) {
                                case RAW, ENCODED, MANUFACTURED -> StorageService.getMaterialCount(item.getKey());
                                case COMMODITY ->
                                        StorageService.getCommodityCount((Commodity) item.getKey(), StoragePool.SHIP) + StorageService.getCommodityCount((Commodity) item.getKey(), StoragePool.FLEETCARRIER);
                                default -> 0;
                            };
                            final Row dataRow = sheet.createRow(rowNumber.getAndIncrement());
                            createCell(sheet, dataRow, 0, materialName, dataStyle);
                            createCell(sheet, dataRow, 1, ship, dataStyle);
                            createCell(sheet, dataRow, 2, fc, dataStyle);
                            createCell(sheet, dataRow, 3, total, dataStyle);
                            createCell(sheet, dataRow, 4, item.getValue(), dataStyle);
                            createCell(sheet, dataRow, 5, Math.max(0, item.getValue() - ship), dataStyle);
                        })
        );
        return workbook;
    }

    private static void createCell(final Sheet sheet, final Row row, final int columnCount, final Object value, final CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        final Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }
}
