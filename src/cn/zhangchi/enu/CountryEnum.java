package cn.zhangchi.enu;

/**
 * 枚举类
 */
public enum CountryEnum {

    ONE(1, "齐"), TWO(2, "楚"),

    THREE(3, "燕"), FOUR(4, "赵"),

    FIVE(5, "魏"), SIX(6, "韩");

    private Integer key;
    private String value;

    CountryEnum(int key, String value) {
        this.key = key;
        this.value = value;
    }


    public static CountryEnum forEach_CountryEnum(int index) {
        CountryEnum[] countryEnums = CountryEnum.values();
        for (CountryEnum countryEnum : countryEnums) {
            if (index == countryEnum.getKey()) {
                return countryEnum;
            }
        }
        return null;
    }

    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
