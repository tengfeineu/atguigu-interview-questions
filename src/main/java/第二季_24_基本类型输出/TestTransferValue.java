package 第二季_24_基本类型输出;

import lombok.extern.slf4j.Slf4j;

/**
 * @author wangtf@citic.com
 * @Title: TestTransferValue
 * @ProjectName cloud2020
 * @Description: TODO
 * @date 2021/9/5 16:44
 */
@Slf4j
public class TestTransferValue {

    public void changeIntegerValue(int age) {
        age = 30;
    }

    public void changePersonValue(Person person) {
        person.setPersonName("XXX");
    }

    public void changeStringValue(String str) {
        str = "XXX";
    }

    public void changeNewStringValue(String str) {
        str = "XXX";
    }

    public static void main(String[] args) {
        // 栈管运行，堆管存储
        // 栈先进后出
        // 基本类型存在栈里
        TestTransferValue testTransferValue = new TestTransferValue();
        int age = 18;
        testTransferValue.changeIntegerValue(age);
        log.info("age :{}", age);

        Person person = new Person("abc");
        testTransferValue.changePersonValue(person);
        log.info("personName: {}", person.getPersonName());

        // String str = "str"; 存在常量池里面
        String str = "str";
        testTransferValue.changeStringValue(str);
        log.info("str :{}", str);

        String str1 = new String("str1");
        testTransferValue.changeNewStringValue(str1);
        log.info("str1 :{}", str1);
    }

}
