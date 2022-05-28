package nl.jixxed.eliteodysseymaterials.service.ar;


public class WindowInfo {
    public int hwnd;
    public RECT rect;
    public int contentX;
    public int contentY;
    public String title;
    long styles;

    public WindowInfo(final int hwnd, final RECT rect, final String title, final long styles) {
        this.hwnd = hwnd;
        this.rect = rect;
        this.title = title;
        this.styles = styles;
    }

    @Override
    public String toString() {
        return String.format("%d @ (%d,%d)-(%d,%d) : \"%s\"", this.hwnd, this.rect.left, this.rect.top, this.rect.right, this.rect.bottom, this.title);
    }
}