package 第二季_24_基本类型输出;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author wangtf@citic.com
 * @Title: Person
 * @ProjectName HighFrequencyInterviewQuestions
 * @Description: TODO
 * @date 2021/9/5 16:35
 */
@NoArgsConstructor
@Getter
@Setter
public class Person {

    private Integer id;
    private String personName;

    public Person(String personName) {
        this.personName = personName;
    }
}
