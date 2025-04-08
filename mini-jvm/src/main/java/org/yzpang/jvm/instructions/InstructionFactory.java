package org.yzpang.jvm.instructions;

import org.yzpang.jvm.instructions.base.CustomInstruction;
import org.yzpang.jvm.instructions.base.NoOperandsInstruction;
import org.yzpang.jvm.instructions.comparisons.*;
import org.yzpang.jvm.instructions.constants.*;
import org.yzpang.jvm.instructions.control.*;
import org.yzpang.jvm.instructions.conversions.*;
import org.yzpang.jvm.instructions.extended.GotowExtendedInstruction;
import org.yzpang.jvm.instructions.extended.IfNonNullExtendedInstruction;
import org.yzpang.jvm.instructions.extended.IfNullExtendedInstruction;
import org.yzpang.jvm.instructions.extended.WideExtendedInstruction;
import org.yzpang.jvm.instructions.loads.*;
import org.yzpang.jvm.instructions.math.*;
import org.yzpang.jvm.instructions.references.*;
import org.yzpang.jvm.instructions.reserved.InvokeNativeReservedInstruction;
import org.yzpang.jvm.instructions.stack.*;
import org.yzpang.jvm.instructions.stores.*;

/**
 * 指令工厂
 * 根据字节码生成相应的指令对象
 */
public class InstructionFactory {

    public static CustomInstruction newInstruction(int opcode) {
        switch (opcode) {
            case 0x00:
                return new NoOperandsInstruction();
            case 0x01:
                return new AConstNULLInstruction();
            case 0x02:
                return new IConstM1Instruction();
            case 0x03:
                return new IConst0Instruction();
            case 0x04:
                return new IConst1Instruction();
            case 0x05:
                return new IConst2Instruction();
            case 0x06:
                return new IConst3Instruction();
            case 0x07:
                return new IConst4Instruction();
            case 0x08:
                return new IConst5Instruction();
            case 0x09:
                return new LConst0Instruction();
            case 0x0a:
                return new LConst1Instruction();
            case 0x0b:
                return new FConst0Instruction();
            case 0x0c:
                return new FConst1Instruction();
            case 0x0d:
                return new FConst2Instruction();
            case 0x0e:
                return new DConst0Instruction();
            case 0x0f:
                return new DConst1Instruction();
            case 0x10:
                return new BipushConstantInstruction();
            case 0x11:
                return new SipushConstantInstruction();
            case 0x12:
                return new LdcConstInstruction();
            case 0x13:
                return new LdcWConstInstruction();
            case 0x14:
                return new Ldc2WConstInstruction();
            case 0x15:
                return new ILoadInstruction();
            case 0x16:
                return new LLoadInstruction();
            case 0x17:
                return new FLoadInstruction();
            case 0x18:
                return new DLoadInstruction();
            case 0x19:
                return new ALoadInstruction();
            case 0x1a:
                return new ILoad0Instruction();
            case 0x1b:
                return new ILoad1Instruction();
            case 0x1c:
                return new ILoad2Instruction();
            case 0x1d:
                return new ILoad3Instruction();
            case 0x1e:
                return new LLoad0Instruction();
            case 0x1f:
                return new LLoad1Instruction();
            case 0x20:
                return new LLoad2Instruction();
            case 0x21:
                return new LLoad3Instruction();
            case 0x22:
                return new FLoad0Instruction();
            case 0x23:
                return new FLoad1Instruction();
            case 0x24:
                return new FLoad2Instruction();
            case 0x25:
                return new FLoad3Instruction();
            case 0x26:
                return new DLoad0Instruction();
            case 0x27:
                return new DLoad1Instruction();
            case 0x28:
                return new DLoad2Instruction();
            case 0x29:
                return new DLoad3Instruction();
            case 0x2a:
                return new ALoad0Instruction();
            case 0x2b:
                return new ALoad1Instruction();
            case 0x2c:
                return new ALoad2Instruction();
            case 0x2d:
                return new ALoad3Instruction();
            case 0x2e:
                return new IaloadInstruction();
            case 0x2f:
                return new LaloadInstruction();
            case 0x30:
                return new FaloadInstruction();
            case 0x31:
                return new DaloadInstruction();
            case 0x32:
                return new AaloadInstruction();
            case 0x33:
                return new BaloadInstruction();
            case 0x34:
                return new CaloadInstruction();
            case 0x35:
                return new SaloadInstruction();
            case 0x36:
                return new IStoreInstruction();
            case 0x37:
                return new LStoreInstruction();
            case 0x38:
                return new FStoreInstruction();
            case 0x39:
                return new DStoreInstruction();
            case 0x3a:
                return new AStoreInstruction();
            case 0x3b:
                return new IStore0Instruction();
            case 0x3c:
                return new IStore1Instruction();
            case 0x3d:
                return new IStore2Instruction();
            case 0x3e:
                return new IStore3Instruction();
            case 0x3f:
                return new LStore0Instruction();
            case 0x40:
                return new LStore1Instruction();
            case 0x41:
                return new LStore2Instruction();
            case 0x42:
                return new LStore3Instruction();
            case 0x43:
                return new FStore0Instruction();
            case 0x44:
                return new FStore1Instruction();
            case 0x45:
                return new FStore2Instruction();
            case 0x46:
                return new FStore3Instruction();
            case 0x47:
                return new DStore0Instruction();
            case 0x48:
                return new DStore1Instruction();
            case 0x49:
                return new DStore2Instruction();
            case 0x4a:
                return new DStore3Instruction();
            case 0x4b:
                return new AStore0Instruction();
            case 0x4c:
                return new AStore1Instruction();
            case 0x4d:
                return new AStore2Instruction();
            case 0x4e:
                return new AStore3Instruction();
            case 0x4f:
                return new IastoreInstruction();
            case 0x50:
                return new LastoreInstruction();
            case 0x51:
                return new FastoreInstruction();
            case 0x52:
                return new DastoreInstruction();
            case 0x53:
                return new AastoreInstruction();
            case 0x54:
                return new BastoreInstruction();
            case 0x55:
                return new CastoreInstruction();
            case 0x56:
                return new SastoreInstruction();
            case 0x57:
                return new PopStackInstruction();
            case 0x58:
                return new Pop2StackInstruction();
            case 0x59:
                return new DupStackInstruction();
            case 0x5a:
                return new DupX1StackInstruction();
            case 0x5b:
                return new DupX2StackInstruction();
            case 0x5c:
                return new Dup2StackInstruction();
            case 0x5d:
                return new Dup2X1StackInstruction();
            case 0x5e:
                return new Dup2X2StackInstruction();
            case 0x5f:
                return new SwapStackInstruction();
            case 0x60:
                return new IAddMathInstruction();
            case 0x61:
                return new LAddMathInstruction();
            case 0x62:
                return new FAddMathInstruction();
            case 0x63:
                return new DAddMathInstruction();
            case 0x64:
                return new ISubMathInstruction();
            case 0x65:
                return new LSubMathInstruction();
            case 0x66:
                return new FSubMathInstruction();
            case 0x67:
                return new DSubMathInstruction();
            case 0x68:
                return new IMulMathInstruction();
            case 0x69:
                return new LMulMathInstruction();
            case 0x6a:
                return new FMulMathInstruction();
            case 0x6b:
                return new DMulMathInstruction();
            case 0x6c:
                return new IDivMathInstruction();
            case 0x6d:
                return new LDivMathInstruction();
            case 0x6e:
                return new FDivMathInstruction();
            case 0x6f:
                return new DDivMathInstruction();
            case 0x70:
                return new IRemMathInstruction();
            case 0x71:
                return new LRemMathInstruction();
            case 0x72:
                return new FRemMathInstruction();
            case 0x73:
                return new DRemMathInstruction();
            case 0x74:
                return new INegMathInstruction();
            case 0x75:
                return new LNegMathInstruction();
            case 0x76:
                return new FNegMathInstruction();
            case 0x77:
                return new DNegMathInstruction();
            case 0x78:
                return new IShlMathInstruction();
            case 0x79:
                return new LShlMathInstruction();
            case 0x7a:
                return new IShrMathInstruction();
            case 0x7b:
                return new LShrMathInstruction();
            case 0x7c:
                return new IUShrMathInstruction();
            case 0x7d:
                return new LUShrMathInstruction();
            case 0x7e:
                return new IAndMathInstruction();
            case 0x7f:
                return new LAndMathInstruction();
            case 0x80:
                return new IOrMathInstruction();
            case 0x81:
                return new LOrMathInstruction();
            case 0x82:
                return new IXorMathInstruction();
            case 0x83:
                return new LXorMathInstruction();
            case 0x84:
                return new IIncMathInstruction();
            case 0x85:
                return new I2LConversionInstruction();
            case 0x86:
                return new I2FConversionInstruction();
            case 0x87:
                return new I2DConversionInstruction();
            case 0x88:
                return new L2IConversionInstruction();
            case 0x89:
                return new L2FConversionInstruction();
            case 0x8a:
                return new L2DConversionInstruction();
            case 0x8b:
                return new F2IConversionInstruction();
            case 0x8c:
                return new F2LConversionInstruction();
            case 0x8d:
                return new F2DConversionInstruction();
            case 0x8e:
                return new D2IConversionInstruction();
            case 0x8f:
                return new D2LConversionInstruction();
            case 0x90:
                return new D2FConversionInstruction();
            case 0x91:
                return new I2BConversionInstruction();
            case 0x92:
                return new I2CConversionInstruction();
            case 0x93:
                return new I2SConversionInstruction();
            case 0x94:
                return new LcmpComparisonInstruction();
            case 0x95:
                return new FcmplComparisonInstruction();
            case 0x96:
                return new FcmpgComparisonInstruction();
            case 0x97:
                return new DcmplComparisonInstruction();
            case 0x98:
                return new DcmpgComparisonInstruction();
            case 0x99:
                return new IfeqComparisonInstruction();
            case 0x9a:
                return new IfneComparisonInstruction();
            case 0x9b:
                return new IfltComparisonInstruction();
            case 0x9c:
                return new IfgeComparisonInstruction();
            case 0x9d:
                return new IfgtComparisonInstruction();
            case 0x9e:
                return new IfleComparisonInstruction();
            case 0x9f:
                return new IficmpeqComparisonInstruction();
            case 0xa0:
                return new IficmpneComparisonInstruction();
            case 0xa1:
                return new IficmpltComparisonInstruction();
            case 0xa2:
                return new IficmpgeComparisonInstruction();
            case 0xa3:
                return new IficmpgtComparisonInstruction();
            case 0xa4:
                return new IficmpleComparisonInstruction();
            case 0xa5:
                return new IfacmpeqComparisonInstruction();
            case 0xa6:
                return new IfacmpneComparisonInstruction();
            case 0xa7:
                return new GotoControlInstruction();
            case 0xa8:
            case 0xa9:
                return null;
            case 0xaa:
                return new TableSwitchControlInstruction();
            case 0xab:
                return new LookupSwitchControlInstruction();
            case 0xac:
                return new IReturnControlInstruction();
            case 0xad:
                return new LReturnControlInstruction();
            case 0xae:
                return new FReturnControlInstruction();
            case 0xaf:
                return new DReturnControlInstruction();
            case 0xb0:
                return new AReturnControlInstruction();
            case 0xb1:
                return new ReturnControlInstruction();
            case 0xb2:
                return new GetStaticReferenceInstruction();
            case 0xb3:
                return new PutStaticReferenceInstruction();
            case 0xb4:
                return new GetFieldReferenceInstruction();
            case 0xb5:
                return new PutFieldReferenceInstruction();
            case 0xb6:
                return new InvokeVirtualReferenceInstruction();
            case 0xb7:
                return new InvokeSpecialReferenceInstruction();
            case 0xb8:
                return new InvokeStaticReferenceInstruction();
            case 0xb9:
                return new InvokeInterfaceReferenceInstruction();
            case 0xba:
                return null;
            case 0xbb:
                return new NewReferenceInstruction();
            case 0xbc:
                return new NewArrayReferenceInstruction();
            case 0xbd:
                return new AnewArrayReferenceInstruction();
            case 0xbe:
                return new ArrayLengthReferenceInstruction();
            case 0xbf:
                return null;
            case 0xc0:
                return new CheckCastReferenceInstruction();
            case 0xc1:
                return new InstanceOfReferenceInstruction();
            case 0xc2:
            case 0xc3:
                return null;
            case 0xc4:
                return new WideExtendedInstruction();
            case 0xc5:
                return new MultiAnewArrayReferenceInstruction();
            case 0xc6:
                return new IfNullExtendedInstruction();
            case 0xc7:
                return new IfNonNullExtendedInstruction();
            case 0xc8:
                return new GotowExtendedInstruction();
            case 0xc9:
            case 0xca:
            case 0xcb:
            case 0xcc:
            case 0xcd:
            case 0xce:
            case 0xcf:
                return null;
            case 0xfe:
                return new InvokeNativeReservedInstruction();
            default:
                break;
        }
        return null;
    }
}
