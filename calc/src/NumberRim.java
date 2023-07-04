public enum NumberRim {
    n0("N"), n1("I"), n4("IV"), n5("V"), n9("IX"), n10("X"), n40("XL"),
    n50("L"), n90("XC"), n100("C"), n400("CD"), n500("D"), n900("CM"), n1000("M");
    private String value;

    NumberRim(String value){
        this.value = value;
    }
    public String getValue(){
        return value;
    }

}
