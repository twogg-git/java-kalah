package com.twogg.kalah.entities;

public class Pit {

    private int stones;
    private int opposite;
    private boolean isKalah;
    private int owner;

    public Pit(int index, int stones) {
        setOwner(index);
        setIsKalah(index);
        setOpposite(index);
        setStones(stones);
    }

    private void setStones(int stones) {
        this.stones = stones;
    }

    private void setIsKalah(int index) {
        if (index == 6 || index == 13) {
            this.isKalah = true;
        } else {
            this.isKalah = false;
        }
    }

    private void setOwner(int index) {
        if (index <= 6) {
            this.owner = 1;
        } else {
            this.owner = 2;
        }
    }

    public int getStones() {
        return stones;
    }

    public int getOpposite() {
        return opposite;
    }

    public boolean getIsKalah() {
        return isKalah;
    }

    public int getOwner() {
        return owner;
    }

    public void addOneStone() {
        this.stones = this.stones + 1;
    }

    public void addCaptureStones(int stones){
        this.stones = this.stones + stones;
    }

    public void emptyPit(){
        this.stones = 0;
    }

    public static int getIndexByAlpha(char pit) {
        switch (pit) {
            case 'A':
                return 0;
            case 'B':
                return 1;
            case 'C':
                return 2;
            case 'D':
                return 3;
            case 'E':
                return 4;
            case 'F':
                return 5;
            case '1':
                return 6;
            case 'G':
                return 7;
            case 'H':
                return 8;
            case 'I':
                return 9;
            case 'J':
                return 10;
            case 'K':
                return 11;
            case 'L':
                return 12;
            case '2':
                return 13;
            default:
                return -1;
        }
    }

    public static char getAlphaByIndex(int pit) {
        switch (pit) {
            case 0:
                return 'A';
            case 1:
                return 'B';
            case 2:
                return 'C';
            case 3:
                return 'D';
            case 4:
                return 'E';
            case 5:
                return 'F';
            case 6:
                return '1';
            case 7:
                return 'G';
            case 8:
                return 'H';
            case 9:
                return 'I';
            case 10:
                return 'J';
            case 11:
                return 'K';
            case 12:
                return 'L';
            case 13:
                return '2';
            default:
                return 'X';
        }
    }

    private void setOpposite(int index) {
        switch (index) {
            case 0:
                this.opposite = 12;
                break;
            case 1:
                this.opposite = 11;
                break;
            case 2:
                this.opposite = 10;
                break;
            case 3:
                this.opposite = 9;
                break;
            case 4:
                this.opposite = 8;
                break;
            case 5:
                this.opposite = 7;
                break;
            case 6:
                this.opposite = 13;
                break;
            case 7:
                this.opposite = 5;
                break;
            case 8:
                this.opposite = 4;
                break;
            case 9:
                this.opposite = 3;
                break;
            case 10:
                this.opposite = 2;
                break;
            case 11:
                this.opposite = 1;
                break;
            case 12:
                this.opposite = 0;
                break;
            case 13:
                this.opposite = 6;
                break;
        }
    }
}
