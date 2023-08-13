package nl.jixxed.eliteodysseymaterials.domain.ships.optional_internals;

public class FrameShiftDriveInterdictor {
//
//		25150 : { mtype:'ifsdi', cost:   12000, name:'Frame Shift Drive Interdictor', class:1, rating:'E', mass: 1.30, integ: 32, pwrdraw:0.14, boottime:15, timerng: 3, facinglim:50, limit:'ifsdi', fdid:128666704, fdname:'Int_FSDInterdictor_Size1_Class1', eddbid:1306 },
//            25140 : { mtype:'ifsdi', cost:   36000, name:'Frame Shift Drive Interdictor', class:1, rating:'D', mass: 0.50, integ: 24, pwrdraw:0.18, boottime:15, timerng: 4, facinglim:50, limit:'ifsdi', fdid:128666708, fdname:'Int_FSDInterdictor_Size1_Class2', eddbid:1310 },
//            25130 : { mtype:'ifsdi', cost:  108000, name:'Frame Shift Drive Interdictor', class:1, rating:'C', mass: 1.30, integ: 40, pwrdraw:0.23, boottime:15, timerng: 5, facinglim:50, limit:'ifsdi', fdid:128666712, fdname:'Int_FSDInterdictor_Size1_Class3', eddbid:1314 },
//            25120 : { mtype:'ifsdi', cost:  324000, name:'Frame Shift Drive Interdictor', class:1, rating:'B', mass: 2.00, integ: 56, pwrdraw:0.28, boottime:15, timerng: 6, facinglim:50, limit:'ifsdi', fdid:128666716, fdname:'Int_FSDInterdictor_Size1_Class4', eddbid:1318 },
//            25110 : { mtype:'ifsdi', cost:  972000, name:'Frame Shift Drive Interdictor', class:1, rating:'A', mass: 1.30, integ: 48, pwrdraw:0.32, boottime:15, timerng: 7, facinglim:50, limit:'ifsdi', fdid:128666720, fdname:'Int_FSDInterdictor_Size1_Class5', eddbid:1322 },
//
//            25250 : { mtype:'ifsdi', cost:   33600, name:'Frame Shift Drive Interdictor', class:2, rating:'E', mass: 2.50, integ: 41, pwrdraw:0.17, boottime:15, timerng: 6, facinglim:50, limit:'ifsdi', fdid:128666705, fdname:'Int_FSDInterdictor_Size2_Class1', eddbid:1307 },
//            25240 : { mtype:'ifsdi', cost:  100800, name:'Frame Shift Drive Interdictor', class:2, rating:'D', mass: 1.00, integ: 31, pwrdraw:0.22, boottime:15, timerng: 7, facinglim:50, limit:'ifsdi', fdid:128666709, fdname:'Int_FSDInterdictor_Size2_Class2', eddbid:1311 },
//            25230 : { mtype:'ifsdi', cost:  302400, name:'Frame Shift Drive Interdictor', class:2, rating:'C', mass: 2.50, integ: 51, pwrdraw:0.28, boottime:15, timerng: 8, facinglim:50, limit:'ifsdi', fdid:128666713, fdname:'Int_FSDInterdictor_Size2_Class3', eddbid:1315 },
//            25220 : { mtype:'ifsdi', cost:  907200, name:'Frame Shift Drive Interdictor', class:2, rating:'B', mass: 4.00, integ: 71, pwrdraw:0.34, boottime:15, timerng: 9, facinglim:50, limit:'ifsdi', fdid:128666717, fdname:'Int_FSDInterdictor_Size2_Class4', eddbid:1319 },
//            25210 : { mtype:'ifsdi', cost: 2721600, name:'Frame Shift Drive Interdictor', class:2, rating:'A', mass: 2.50, integ: 61, pwrdraw:0.39, boottime:15, timerng:10, facinglim:50, limit:'ifsdi', fdid:128666721, fdname:'Int_FSDInterdictor_Size2_Class5', eddbid:1323 },
//
//            25350 : { mtype:'ifsdi', cost:   94080, name:'Frame Shift Drive Interdictor', class:3, rating:'E', mass: 5.00, integ: 51, pwrdraw:0.20, boottime:15, timerng: 9, facinglim:50, limit:'ifsdi', fdid:128666706, fdname:'Int_FSDInterdictor_Size3_Class1', eddbid:1308 },
//            25340 : { mtype:'ifsdi', cost:  282240, name:'Frame Shift Drive Interdictor', class:3, rating:'D', mass: 2.00, integ: 38, pwrdraw:0.27, boottime:15, timerng:10, facinglim:50, limit:'ifsdi', fdid:128666710, fdname:'Int_FSDInterdictor_Size3_Class2', eddbid:1312 },
//            25330 : { mtype:'ifsdi', cost:  846720, name:'Frame Shift Drive Interdictor', class:3, rating:'C', mass: 5.00, integ: 64, pwrdraw:0.34, boottime:15, timerng:11, facinglim:50, limit:'ifsdi', fdid:128666714, fdname:'Int_FSDInterdictor_Size3_Class3', eddbid:1316 },
//            25320 : { mtype:'ifsdi', cost: 2540160, name:'Frame Shift Drive Interdictor', class:3, rating:'B', mass: 8.00, integ: 90, pwrdraw:0.41, boottime:15, timerng:12, facinglim:50, limit:'ifsdi', fdid:128666718, fdname:'Int_FSDInterdictor_Size3_Class4', eddbid:1320 },
//            25310 : { mtype:'ifsdi', cost: 7620480, name:'Frame Shift Drive Interdictor', class:3, rating:'A', mass: 5.00, integ: 77, pwrdraw:0.48, boottime:15, timerng:13, facinglim:50, limit:'ifsdi', fdid:128666722, fdname:'Int_FSDInterdictor_Size3_Class5', eddbid:1324 },
//
//            25450 : { mtype:'ifsdi', cost:  263420, name:'Frame Shift Drive Interdictor', class:4, rating:'E', mass:10.00, integ: 64, pwrdraw:0.25, boottime:15, timerng:12, facinglim:50, limit:'ifsdi', fdid:128666707, fdname:'Int_FSDInterdictor_Size4_Class1', eddbid:1309 },
//            25440 : { mtype:'ifsdi', cost:  790270, name:'Frame Shift Drive Interdictor', class:4, rating:'D', mass: 4.00, integ: 48, pwrdraw:0.33, boottime:15, timerng:13, facinglim:50, limit:'ifsdi', fdid:128666711, fdname:'Int_FSDInterdictor_Size4_Class2', eddbid:1313 },
//            25430 : { mtype:'ifsdi', cost: 2370820, name:'Frame Shift Drive Interdictor', class:4, rating:'C', mass:10.00, integ: 80, pwrdraw:0.41, boottime:15, timerng:14, facinglim:50, limit:'ifsdi', fdid:128666715, fdname:'Int_FSDInterdictor_Size4_Class3', eddbid:1317 },
//            25420 : { mtype:'ifsdi', cost: 7112450, name:'Frame Shift Drive Interdictor', class:4, rating:'B', mass:16.00, integ:112, pwrdraw:0.49, boottime:15, timerng:15, facinglim:50, limit:'ifsdi', fdid:128666719, fdname:'Int_FSDInterdictor_Size4_Class4', eddbid:1321 },
//            25410 : { mtype:'ifsdi', cost:21337340, name:'Frame Shift Drive Interdictor', class:4, rating:'A', mass:10.00, integ: 96, pwrdraw:0.57, boottime:15, timerng:16, facinglim:50, limit:'ifsdi', fdid:128666723, fdname:'Int_FSDInterdictor_Size4_Class5', eddbid:1325 },
//

}
