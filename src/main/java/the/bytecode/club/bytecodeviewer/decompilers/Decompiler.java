package the.bytecode.club.bytecodeviewer.decompilers;

import the.bytecode.club.bytecodeviewer.decompilers.impl.*;

/***************************************************************************
 * Bytecode Viewer (BCV) - Java & Android Reverse Engineering Suite        *
 * Copyright (C) 2014 Kalen 'Konloch' Kinloch - http://bytecodeviewer.com  *
 *                                                                         *
 * This program is free software: you can redistribute it and/or modify    *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation, either version 3 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 *   This program is distributed in the hope that it will be useful,       *
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of        *
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the         *
 *   GNU General Public License for more details.                          *
 *                                                                         *
 *   You should have received a copy of the GNU General Public License     *
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>. *
 ***************************************************************************/

/**
 * All of the decompilers/disassemblers BCV uses
 *
 * @author Konloch
 */
public enum Decompiler
{
    //TODO WARNING: do not change the decompiler order, when adding a new decompiler just add it to the end
    // enum ordinal is used for settings serialization instead of the enum name
    NONE("None", null),
    PROCYON_DECOMPILER("Procyon Decompiler", new ProcyonDecompiler()),
    CFR_DECOMPILER("CFR Decompiler", new CFRDecompiler()),
    FERNFLOWER_DECOMPILER("FernFlower Decompiler", new FernFlowerDecompiler()),
    BYTECODE_DISASSEMBLER("Bytecode Disassembler", new BytecodeDisassembler()),
    HEXCODE_VIEWER("Hexcode Viewer", null),
    SMALI_DISASSEMBLER("Smali Disassembler", new SmaliDisassembler()),
    KRAKATAU_DECOMPILER("Krakatau Decompiler", new KrakatauDecompiler()),
    KRAKATAU_DISASSEMBLER("Krakatau Disassembler", new KrakatauDisassembler()),
    JD_DECOMPILER("JD-GUI Decompiler", new JDGUIDecompiler()),
    JADX_DECOMPILER("JADX Decompiler", new JADXDecompiler()),
    ASM_TEXTIFY_DISASSEMBLER("ASM Disassembler", new ASMTextifierDecompiler()),
    ;
    
    private final String decompilerName;
    private final InternalDecompiler decompiler;
    
    Decompiler(String decompilerName, InternalDecompiler decompiler) {
        this.decompilerName = decompilerName;
        this.decompiler = decompiler;
    }
    
    public String getDecompilerName()
    {
        return decompilerName;
    }
    
    public InternalDecompiler getDecompiler()
    {
        return decompiler;
    }
}