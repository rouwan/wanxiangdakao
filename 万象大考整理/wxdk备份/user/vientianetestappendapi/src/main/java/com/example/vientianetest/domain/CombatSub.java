package com.example.vientianetest.domain;

public class CombatSub {
    private int parentType; //1:德,2:商,3:文,4:理,5:艺,6:体,7:综
    private int combat;   //体力

    public CombatSub(){}

    public CombatSub(int parentType,int combat) {
        this.parentType = parentType;
        this.combat = combat;
    }

    public int getParentType() {
        return parentType;
    }

    public void setParentType(int parentType) {
        this.parentType = parentType;
    }

    public int getCombat() {
        return combat;
    }

    public void setCombat(int combat) {
        this.combat = combat;
    }
}
