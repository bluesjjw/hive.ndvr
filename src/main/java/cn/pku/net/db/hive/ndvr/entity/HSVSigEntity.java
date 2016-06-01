/**
 * @Title: HSVSigEntity.java 
 * @Package cn.pku.net.db.hive.ndvr.entity 
 * @Description: TODO
 * @author Jiawei Jiang    
 * @date 2014年10月24日 下午3:34:54 
 * School of EECS, Peking University
 * Copyright (c) All Rights Reserved.
 */
package cn.pku.net.db.hive.ndvr.entity;

/**
 * @ClassName: HSVSigEntity
 * @Description: TODO
 * @author Jiawei Jiang
 * @date 2014年10月24日 下午3:34:54
 */
public class HSVSigEntity {

    private float bin1;
    private float bin2;
    private float bin3;
    private float bin4;
    private float bin5;
    private float bin6;
    private float bin7;
    private float bin8;
    private float bin9;
    private float bin10;
    private float bin11;
    private float bin12;
    private float bin13;
    private float bin14;
    private float bin15;
    private float bin16;
    private float bin17;
    private float bin18;
    private float bin19;
    private float bin20;
    private float bin21;
    private float bin22;
    private float bin23;
    private float bin24;

    public HSVSigEntity(String[] lines) {
        super();
        this.bin1 = Float.valueOf(lines[1]);
        this.bin2 = Float.valueOf(lines[2]);
        this.bin3 = Float.valueOf(lines[3]);
        this.bin4 = Float.valueOf(lines[4]);
        this.bin5 = Float.valueOf(lines[5]);
        this.bin6 = Float.valueOf(lines[6]);
        this.bin7 = Float.valueOf(lines[7]);
        this.bin8 = Float.valueOf(lines[8]);
        this.bin9 = Float.valueOf(lines[9]);
        this.bin10 = Float.valueOf(lines[10]);
        this.bin11 = Float.valueOf(lines[11]);
        this.bin12 = Float.valueOf(lines[12]);
        this.bin13 = Float.valueOf(lines[13]);
        this.bin14 = Float.valueOf(lines[14]);
        this.bin15 = Float.valueOf(lines[15]);
        this.bin16 = Float.valueOf(lines[16]);
        this.bin17 = Float.valueOf(lines[17]);
        this.bin18 = Float.valueOf(lines[18]);
        this.bin19 = Float.valueOf(lines[19]);
        this.bin20 = Float.valueOf(lines[20]);
        this.bin21 = Float.valueOf(lines[21]);
        this.bin22 = Float.valueOf(lines[21]);
        this.bin23 = Float.valueOf(lines[23]);
        this.bin24 = Float.valueOf(lines[24]);
    }

    /**
     * @param hsvSignature
     */
    public HSVSigEntity(float[] hsvSignature) {
        super();
        this.bin1 = hsvSignature[0];
        this.bin2 = hsvSignature[1];
        this.bin3 = hsvSignature[2];
        this.bin4 = hsvSignature[3];
        this.bin5 = hsvSignature[4];
        this.bin6 = hsvSignature[5];
        this.bin7 = hsvSignature[6];
        this.bin8 = hsvSignature[7];
        this.bin9 = hsvSignature[8];
        this.bin10 = hsvSignature[9];
        this.bin11 = hsvSignature[10];
        this.bin12 = hsvSignature[11];
        this.bin13 = hsvSignature[12];
        this.bin14 = hsvSignature[13];
        this.bin15 = hsvSignature[14];
        this.bin16 = hsvSignature[15];
        this.bin17 = hsvSignature[16];
        this.bin18 = hsvSignature[17];
        this.bin19 = hsvSignature[18];
        this.bin20 = hsvSignature[19];
        this.bin21 = hsvSignature[20];
        this.bin22 = hsvSignature[21];
        this.bin23 = hsvSignature[22];
        this.bin24 = hsvSignature[23];
    }

    /**
     * Getter method for property <tt>bin1</tt>.
     * 
     * @return property value of bin1
     */
    public float getBin1() {
        return bin1;
    }

    /**
     * Setter method for property <tt>bin1</tt>.
     * 
     * @param bin1 value to be assigned to property bin1
     */
    public void setBin1(float bin1) {
        this.bin1 = bin1;
    }

    /**
     * Getter method for property <tt>bin2</tt>.
     * 
     * @return property value of bin2
     */
    public float getBin2() {
        return bin2;
    }

    /**
     * Setter method for property <tt>bin2</tt>.
     * 
     * @param bin2 value to be assigned to property bin2
     */
    public void setBin2(float bin2) {
        this.bin2 = bin2;
    }

    /**
     * Getter method for property <tt>bin3</tt>.
     * 
     * @return property value of bin3
     */
    public float getBin3() {
        return bin3;
    }

    /**
     * Setter method for property <tt>bin3</tt>.
     * 
     * @param bin3 value to be assigned to property bin3
     */
    public void setBin3(float bin3) {
        this.bin3 = bin3;
    }

    /**
     * Getter method for property <tt>bin4</tt>.
     * 
     * @return property value of bin4
     */
    public float getBin4() {
        return bin4;
    }

    /**
     * Setter method for property <tt>bin4</tt>.
     * 
     * @param bin4 value to be assigned to property bin4
     */
    public void setBin4(float bin4) {
        this.bin4 = bin4;
    }

    /**
     * Getter method for property <tt>bin5</tt>.
     * 
     * @return property value of bin5
     */
    public float getBin5() {
        return bin5;
    }

    /**
     * Setter method for property <tt>bin5</tt>.
     * 
     * @param bin5 value to be assigned to property bin5
     */
    public void setBin5(float bin5) {
        this.bin5 = bin5;
    }

    /**
     * Getter method for property <tt>bin6</tt>.
     * 
     * @return property value of bin6
     */
    public float getBin6() {
        return bin6;
    }

    /**
     * Setter method for property <tt>bin6</tt>.
     * 
     * @param bin6 value to be assigned to property bin6
     */
    public void setBin6(float bin6) {
        this.bin6 = bin6;
    }

    /**
     * Getter method for property <tt>bin7</tt>.
     * 
     * @return property value of bin7
     */
    public float getBin7() {
        return bin7;
    }

    /**
     * Setter method for property <tt>bin7</tt>.
     * 
     * @param bin7 value to be assigned to property bin7
     */
    public void setBin7(float bin7) {
        this.bin7 = bin7;
    }

    /**
     * Getter method for property <tt>bin8</tt>.
     * 
     * @return property value of bin8
     */
    public float getBin8() {
        return bin8;
    }

    /**
     * Setter method for property <tt>bin8</tt>.
     * 
     * @param bin8 value to be assigned to property bin8
     */
    public void setBin8(float bin8) {
        this.bin8 = bin8;
    }

    /**
     * Getter method for property <tt>bin9</tt>.
     * 
     * @return property value of bin9
     */
    public float getBin9() {
        return bin9;
    }

    /**
     * Setter method for property <tt>bin9</tt>.
     * 
     * @param bin9 value to be assigned to property bin9
     */
    public void setBin9(float bin9) {
        this.bin9 = bin9;
    }

    /**
     * Getter method for property <tt>bin10</tt>.
     * 
     * @return property value of bin10
     */
    public float getBin10() {
        return bin10;
    }

    /**
     * Setter method for property <tt>bin10</tt>.
     * 
     * @param bin10 value to be assigned to property bin10
     */
    public void setBin10(float bin10) {
        this.bin10 = bin10;
    }

    /**
     * Getter method for property <tt>bin11</tt>.
     * 
     * @return property value of bin11
     */
    public float getBin11() {
        return bin11;
    }

    /**
     * Setter method for property <tt>bin11</tt>.
     * 
     * @param bin11 value to be assigned to property bin11
     */
    public void setBin11(float bin11) {
        this.bin11 = bin11;
    }

    /**
     * Getter method for property <tt>bin12</tt>.
     * 
     * @return property value of bin12
     */
    public float getBin12() {
        return bin12;
    }

    /**
     * Setter method for property <tt>bin12</tt>.
     * 
     * @param bin12 value to be assigned to property bin12
     */
    public void setBin12(float bin12) {
        this.bin12 = bin12;
    }

    /**
     * Getter method for property <tt>bin13</tt>.
     * 
     * @return property value of bin13
     */
    public float getBin13() {
        return bin13;
    }

    /**
     * Setter method for property <tt>bin13</tt>.
     * 
     * @param bin13 value to be assigned to property bin13
     */
    public void setBin13(float bin13) {
        this.bin13 = bin13;
    }

    /**
     * Getter method for property <tt>bin14</tt>.
     * 
     * @return property value of bin14
     */
    public float getBin14() {
        return bin14;
    }

    /**
     * Setter method for property <tt>bin14</tt>.
     * 
     * @param bin14 value to be assigned to property bin14
     */
    public void setBin14(float bin14) {
        this.bin14 = bin14;
    }

    /**
     * Getter method for property <tt>bin15</tt>.
     * 
     * @return property value of bin15
     */
    public float getBin15() {
        return bin15;
    }

    /**
     * Setter method for property <tt>bin15</tt>.
     * 
     * @param bin15 value to be assigned to property bin15
     */
    public void setBin15(float bin15) {
        this.bin15 = bin15;
    }

    /**
     * Getter method for property <tt>bin16</tt>.
     * 
     * @return property value of bin16
     */
    public float getBin16() {
        return bin16;
    }

    /**
     * Setter method for property <tt>bin16</tt>.
     * 
     * @param bin16 value to be assigned to property bin16
     */
    public void setBin16(float bin16) {
        this.bin16 = bin16;
    }

    /**
     * Getter method for property <tt>bin17</tt>.
     * 
     * @return property value of bin17
     */
    public float getBin17() {
        return bin17;
    }

    /**
     * Setter method for property <tt>bin17</tt>.
     * 
     * @param bin17 value to be assigned to property bin17
     */
    public void setBin17(float bin17) {
        this.bin17 = bin17;
    }

    /**
     * Getter method for property <tt>bin18</tt>.
     * 
     * @return property value of bin18
     */
    public float getBin18() {
        return bin18;
    }

    /**
     * Setter method for property <tt>bin18</tt>.
     * 
     * @param bin18 value to be assigned to property bin18
     */
    public void setBin18(float bin18) {
        this.bin18 = bin18;
    }

    /**
     * Getter method for property <tt>bin19</tt>.
     * 
     * @return property value of bin19
     */
    public float getBin19() {
        return bin19;
    }

    /**
     * Setter method for property <tt>bin19</tt>.
     * 
     * @param bin19 value to be assigned to property bin19
     */
    public void setBin19(float bin19) {
        this.bin19 = bin19;
    }

    /**
     * Getter method for property <tt>bin20</tt>.
     * 
     * @return property value of bin20
     */
    public float getBin20() {
        return bin20;
    }

    /**
     * Setter method for property <tt>bin20</tt>.
     * 
     * @param bin20 value to be assigned to property bin20
     */
    public void setBin20(float bin20) {
        this.bin20 = bin20;
    }

    /**
     * Getter method for property <tt>bin21</tt>.
     * 
     * @return property value of bin21
     */
    public float getBin21() {
        return bin21;
    }

    /**
     * Setter method for property <tt>bin21</tt>.
     * 
     * @param bin21 value to be assigned to property bin21
     */
    public void setBin21(float bin21) {
        this.bin21 = bin21;
    }

    /**
     * Getter method for property <tt>bin22</tt>.
     * 
     * @return property value of bin22
     */
    public float getBin22() {
        return bin22;
    }

    /**
     * Setter method for property <tt>bin22</tt>.
     * 
     * @param bin22 value to be assigned to property bin22
     */
    public void setBin22(float bin22) {
        this.bin22 = bin22;
    }

    /**
     * Getter method for property <tt>bin23</tt>.
     * 
     * @return property value of bin23
     */
    public float getBin23() {
        return bin23;
    }

    /**
     * Setter method for property <tt>bin23</tt>.
     * 
     * @param bin23 value to be assigned to property bin23
     */
    public void setBin23(float bin23) {
        this.bin23 = bin23;
    }

    /**
     * Getter method for property <tt>bin24</tt>.
     * 
     * @return property value of bin24
     */
    public float getBin24() {
        return bin24;
    }

    /**
     * Setter method for property <tt>bin24</tt>.
     * 
     * @param bin24 value to be assigned to property bin24
     */
    public void setBin24(float bin24) {
        this.bin24 = bin24;
    }

}
