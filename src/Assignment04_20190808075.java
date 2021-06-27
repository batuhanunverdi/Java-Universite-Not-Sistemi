import java.util.ArrayList;
import java.util.Comparator;
/**
 * @author Mert Batuhan UNVERDI
 * @since 22.05.2021
 */
public class Assignment04_20190808075 {
    public static void main(String[] args) {
        Department cse = new Department("CSE","Computer Engineering");
        Teacher teacher = new Teacher("Joseph LEDET","josephledet" +
                "@akdeniz.edu.tr",123L,cse,3);
        System.out.println(teacher);
        Student stu = new Student("Assignment 4 STUDENT","me@" +
                "somewhere.com",456L,cse);
        Semester s1 = new Semester(1,2020);
        Course c101 = new Course(cse,101,"Programming 1",
                6,teacher);
        Semester s2 = new Semester(2,2021);
        Course c102 = new Course(cse,102,"Programming 2",
                4,teacher);
        Course c204 = new Course(cse,204,"Database System",
                6,teacher);

        stu.addCourse(c101,s1,80);
        stu.addCourse(c102,s2,30);
        stu.addCourse(c204,s2,70);
        System.out.println("List Grades for CSE 101:\n"+ stu.listGrades(c101));
        System.out.println("List Grades for Spring 2021:\n"+stu.listGrades(s2));
        System.out.println("Student Transcript:\n"+stu.transcript());

        GradStudent gs = new GradStudent("Assignment 4 GRADSTUDENT",
                "me@somewhere.com",789L,cse,"MDE");
        gs.addCourse(c101,s1,85);
        gs.addCourse(c102,s1,40);
        gs.setTeachingAssistant(c101);
        System.out.println("Teaching Assistant:\n" + gs.getTeachingAssistant());
        gs.setTeachingAssistant(c102);
        System.out.println("Teaching Assistant:\n" + gs.getTeachingAssistant());
    }
}
class Department{
    private String id;
    private String name;
    private Teacher chair;

    public Department(String id, String name) {
        if (id.length() == 3 || id.length() == 4)
            this.id = id;
        else
            throw new InvalidIDException(id);
        this.name = name;
    }

    public String getID() {
        return this.id;
    }

    public void setID(String id) {
        if (id.length() == 3 || id.length() == 4)
            this.id = id;
        else
            throw new InvalidIDException(id);
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Teacher getChair() {
        return chair;
    }

    public void setChair(Teacher chair) {
        if(chair == null){
            this.chair = null;
        }
        else if(this.chair == null)
        {
            if(this == chair.getDepartment())
                this.chair = chair;
            else
                throw new DepartmentMismatchException(this, chair);
        }
        else {
            if (this == chair.getDepartment()) {
                this.chair = chair;
            } else
                throw new DepartmentMismatchException(this, chair);
        }
    }
}
class Course{
    private Department department;
    private Teacher teacher;
    private int number;
    private String title;
    private int akts;

    public Course(Department department,int number, String title,
                  int akts,Teacher teacher) {
        this.department = department;
        this.teacher = teacher;
        this.title = title;
        if ((number >= 100 && number <= 499) || (number >= 5000 &&
                number <= 5999) ||(number >= 7000 && number <= 7999))
            this.number = number;
        else
            throw new InvalidNumberException(number);

        if (akts > 0)
            this.akts = akts;
        else
            throw new InvalidAKTSException(akts);

        if(teacher.getDepartment()!=this.department)
        {
            throw new DepartmentMismatchException(this,teacher);
        }
    }

    public Department getDepartment() {
        return this.department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        if(teacher.getDepartment()!=this.department){
            throw new DepartmentMismatchException(this,teacher);
        }
        else{
            this.teacher = teacher;
        }

    }
    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        if ((number >= 100 && number <= 499) || (number >= 5000 &&
                number <= 5999) || ((number >= 7000 && number <= 7999)))
            this.number = number;
        else
            throw new InvalidNumberException(number);
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAKTS() {
        return this.akts;
    }

    public void setAKTS(int akts) {
        if (akts > 0)
            this.akts = akts;
        else
            throw new InvalidAKTSException(akts);
    }
    public String courseCode() {
        return this.department.getID() + " " + getNumber();
    }
    public String toString() {
        return this.department.getID()+" "+this.number+" - "+this.title
                +" ("+this.akts+")";
    }
}
abstract class Person{
    private Department department;
    private String name;
    private String email;
    private long id;

    public Person(String name, String email, long id,Department department) {
        this.name = name;
        if (email.matches("(.*)@(.+)\\.com")||
                email.matches("(.*)@(.+)\\.edu\\.tr"))
            this.email = email;
        else
            throw new InvalidEmailException(email);
        this.id = id;
        this.department = department;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        if (email.matches("(.*)@(.+)\\.com")||
                email.matches("(.*)@(.+)\\.edu\\.tr"))
            this.email = email;
        else
            throw new InvalidEmailException(email);
    }

    public long getID() {
        return this.id;
    }

    public void setID(long id) {
        this.id = id;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department){
        this.department = department;
    }

    public String toString() {
        return getName() + " " +"("+ getID() +")"+ " - " + getEmail();
    }
}
class Teacher extends Person{
    private int rank;

    public Teacher(String name, String email, long id, Department department,
                   int rank) {
        super(name, email, id, department);
        if(rank>=1&&rank<=5)
            this.rank = rank;
        else
            throw new InvalidRankException(rank);
    }

    @Override
    public void setDepartment(Department department) {
        if(this==this.getDepartment().getChair())
        {
            this.getDepartment().setChair(null);
        }
        super.setDepartment(department);
    }
    public int getRank() {
        return this.rank;
    }

    public String getTitle() {
        return switch (rank) {
            case 5 -> "Professor";
            case 4 -> "Associate Professor";
            case 3 -> "Assistant Professor";
            case 2 -> "Lecturer";
            case 1 -> "Adjunct Instructor";
            default -> "";
        };
    }

    public void promote() {
        if (rank >= 1 && rank <= 4)
            rank += 1;
        else
        {
            throw new InvalidRankException(rank+1);
        }
    }

    public void demote() {
        if (rank >= 2 && rank <= 5)
            rank -= 1;
        else{
            throw new InvalidRankException(rank-1);
        }
    }

    public String toString() {
        return getTitle() + " " + super.toString();
    }
}
class Student extends Person{
    private  ArrayList<Course> courseList = new ArrayList<>();
    private  ArrayList<Double> gradeList = new ArrayList<>();
    private  ArrayList<Semester> semesterList = new ArrayList<>();
    public Student(String name, String email, long id, Department department) {
        super(name, email, id, department);
    }

    public ArrayList<Course> getCourseList() {
        return courseList;
    }
    public ArrayList<Double> getGradeList() {
        return gradeList;
    }
    public ArrayList<Semester> getSemesterList() {
        return semesterList;
    }

    public int getAKTS() {
        int a = 0;
        ArrayList<Course> courseControl = new ArrayList<>();
        for (int i = 0; i <getCourseList().size() ; i++) {
            for (int j = i; j <getCourseList().size() ; j++) {
                    if(getGradeList().get(j)>=60 && getGradeList().get(j)<=100){
                        if(!courseControl.contains(getCourseList().get(j))) {
                            a += getCourseList().get(j).getAKTS();
                            courseControl.add(getCourseList().get(j));
                        }
                    }
                }
            }
        return a;
        }

    public int getAttemptedAKTS(){
        int a =0;
        for (int i = 0; i <getGradeList().size() ; i++) {
            int j;
            for (j = 0; j <i ; j++) {
                if(getCourseList().get(i)==getCourseList().get(j))
                    break;
            }
            if(i==j)
                a += getCourseList().get(i).getAKTS();
        }
        return a;
    }
    public void addCourse(Course course,Semester semester,double grade){
        if(grade<0 || grade>100) {
            throw new InvalidGradeException(grade);
        }
        else if (getCourseList().contains(course)&&getSemesterList().contains
                (semester)){
            boolean isSet = false;
            for (int i = getCourseList().indexOf(course); i<getSemesterList().
                    size();i++) {
                if(getCourseList().get(i)==course&&getSemesterList().get(i)==
                        semester){
                    getGradeList().set(i,grade);
                    isSet = true;
                    break;
                }
        }
            if(!isSet){
                getCourseList().add(course);
                getSemesterList().add(semester);
                getGradeList().add(grade);
            }
        }
        else{
            getCourseList().add(course);
            getSemesterList().add(semester);
            getGradeList().add(grade);
        }
    }
    public double courseGPAPoints(Course course)
    {   if(getCourseList().contains(course)) {
            double a = getGradeList().get(getCourseList().indexOf(course));
            for (int i = getCourseList().indexOf(course);i<getCourseList().
                    size();i++) {
                if(getCourseList().get(i)==course)
                {
                    if(a < getGradeList().get(i))
                    {
                        a = getGradeList().get(i);
                    }
                }
            }
        if (a >= 88 && a <= 100)
            return 4.0;
        else if (a < 88 && a >= 81)
            return 3.5;
        else if (a < 81 && a >= 74)
            return 3.0;
        else if (a < 74 && a >= 67)
            return 2.5;
        else if (a < 67 && a >= 60)
            return 2.0;
        else if (a < 60 && a >= 53)
            return 1.5;
        else if (a < 53 && a >= 46)
            return 1.0;
        else if (a < 46 && a >= 35)
            return 0.5;
        else return 0.0;
    }
    else
        throw new CourseNotFoundException(this,course);
    }
    public double courseGPAPoints(double grade){
        if (grade >= 88 && grade <= 100)
            return 4.0;
        else if (grade < 88 && grade >= 81)
            return 3.5;
        else if (grade < 81 && grade >= 74)
            return 3.0;
        else if (grade < 74 && grade >= 67)
            return 2.5;
        else if (grade < 67 && grade >= 60)
            return 2.0;
        else if (grade < 60 && grade >= 53)
            return 1.5;
        else if (grade < 53 && grade >= 46)
            return 1.0;
        else if (grade < 46 && grade >= 35)
            return 0.5;
        else return 0.0;

    }
    public String courseGradeLetter(Course course){
        if(getCourseList().contains(course)) {
            double a = getGradeList().get(getCourseList().indexOf(course));
            for (int i = getCourseList().indexOf(course); i<getCourseList().
                    size();i++) {
                if(getCourseList().get(i)==course)
                {
                    if(a < getGradeList().get(i))
                    {
                        a = getGradeList().get(i);
                    }
                }
            }
            if (a >= 88 && a <= 100)
                return "AA";
            else if (a < 88 && a >= 81)
                return "BA";
            else if (a < 81 && a >= 74)
                return "BB";
            else if (a < 74 && a >= 67)
                return "CB";
            else if (a < 67 && a >= 60)
                return "CC";
            else if (a < 60 && a >= 53)
                return "DC";
            else if (a < 53 && a >= 46)
                return "DD";
            else if (a < 46 && a >= 35)
                return "FD";
            else return "FF";
        }
        else
            throw new CourseNotFoundException(this,course);

    }
    public String courseGradeLetter(double grade){
            if (grade >= 88 && grade <= 100)
                return "AA";
            else if (grade < 88 && grade >= 81)
                return "BA";
            else if (grade < 81 && grade >= 74)
                return "BB";
            else if (grade < 74 && grade >= 67)
                return "CB";
            else if (grade < 67 && grade >= 60)
                return "CC";
            else if (grade < 60 && grade >= 53)
                return "DC";
            else if (grade < 53 && grade >= 46)
                return "DD";
            else if (grade < 46 && grade >= 35)
                return "FD";
            else return "FF";

    }
    public String courseResult(Course course) {
        if(getCourseList().contains(course)){
            double a = getGradeList().get(getCourseList().indexOf(course));
            for (int i = getCourseList().indexOf(course); i<getCourseList().
                    size();i++) {
                if(getCourseList().get(i)==course)
                {
                    if(a < getGradeList().get(i))
                    {
                        a = getGradeList().get(i);
                    }
                }
            }
            if (a >= 60 && a <= 100)
                return "Passed";
            else if (a < 60 && a >= 46)
                return "Conditionally Passed";
            else
                return "Failed";
        }
        else
            throw new CourseNotFoundException(this,course);
    }
    public String listGrades(Semester semester){
        if(getSemesterList().contains(semester)){
            String s = "";
            for (int i = getSemesterList().indexOf(semester);
                 i<getSemesterList().size();
                 i++) {
                if(getSemesterList().get(getSemesterList().indexOf(semester))==
                        getSemesterList().get(i)) {
                    s +=getCourseList().get(i).courseCode()+" - "+
                            this.courseGradeLetter(getGradeList().get(i))+"\n";
                }
            }
            return s;
        }
        else
            throw new SemesterNotFoundException(this,semester);
    }
    public String listGrades(Course course){
        if(getCourseList().contains(course)){
            String s = "";
            for (int i = getCourseList().indexOf(course); i<getCourseList().
                    size();i++)
            {
                if(getCourseList().get(getCourseList().indexOf(course))==
                        getCourseList().get(i)) {
                    s +=getSemesterList().get(i)+" - "+
                            this.courseGradeLetter(getGradeList().get(i))+"\n";
                }
            }
            return s;
            }
        else
            throw new CourseNotFoundException(this,course);

    }
    public double getGPA()
    {
        double a = 0;
        if(getCourseList().size()>0) {
            ArrayList<Course> cal = new ArrayList<>();
            for (int i = 0; i <getCourseList().size() ; i++) {
                if(!cal.contains(getCourseList().get(i))){
                    cal.add(getCourseList().get(i));
                }
            }
            for (int i = 0; i < cal.size(); i++) {
                a += courseGPAPoints(cal.get(i))
                        * cal.get(i).getAKTS();
            }
            return a / getAttemptedAKTS();
        }
        else
            return 0;

    }
    public String transcript(){
        ArrayList<Semester> sal = new ArrayList<>();
        for (Semester semester : getSemesterList()) {
            if (!sal.contains(semester)) {
                sal.add(semester);
            }
        }
        sal.sort(new SortSemester().thenComparing(Semester::getSeason));
        String s = "";
        for (Semester semester : sal) {
            double a = 0;
            double b = 0;
            s += semester + "\n";
            for (int j = 0; j < getCourseList().size(); j++) {
                if (semester == getSemesterList().get(j)) {
                    s += "\t" + getCourseList().get(j).courseCode() + " - "
                            + courseGradeLetter(getGradeList().get(j)) + "\n";
                    a += courseGPAPoints(getGradeList().get(j)) *
                            getCourseList().get(j).
                            getAKTS();
                    b += getCourseList().get(j).getAKTS();
                }
            }
            s += "GPA: - " + a / b + "\n";
        }
        s+="Overall GPA: "+getGPA();
        return s;
    }
    public String toString()
    {
        return super.toString()+" - GPA: "+getGPA();
    }

}
class GradStudent extends Student{
    private String thesis;
    private Course teachingAssistant;

    public GradStudent(String name, String email, long id, Department department
            , String thesis) {
        super(name, email, id, department);
        this.thesis = thesis;
    }

    public String getThesis() {
        return thesis;
    }

    public void setThesis(String thesis) {
        this.thesis = thesis;
    }

    public double courseGPAPoints(Course course)
    {
        if(getCourseList().contains(course)) {
            double a = getGradeList().get(getCourseList().indexOf(course));
            if (a >= 90 && a <= 100)
                return 4.0;
            else if (a < 90 && a >= 85)
                return 3.5;
            else if (a < 85 && a >= 80)
                return 3.0;
            else if (a < 80 && a >= 75)
                return 2.5;
            else if (a < 75 && a >= 70)
                return 2.0;
            else
                return 0.0;
        }
        else{
            throw new CourseNotFoundException(this,course);
        }
    }
    public double courseGPAPoints(double grade){
        if (grade >= 90 && grade <= 100)
            return 4.0;
        else if (grade < 90 && grade >= 85)
            return 3.5;
        else if (grade < 85 && grade >= 80)
            return 3.0;
        else if (grade < 80 && grade >= 75)
            return 2.5;
        else if (grade < 75 && grade >= 70)
            return 2.0;
        else return 0.0;
    }
    public String courseGradeLetter(Course course){

        if(getCourseList().contains(course)) {
            double a = getGradeList().get(getCourseList().indexOf(course));
            if (a >= 90 && a <= 100)
                return "AA";
            else if (a < 90 && a >= 85)
                return "BA";
            else if (a < 85 && a >= 80)
                return "BB";
            else if (a < 80 && a >= 75)
                return "CB";
            else if (a < 75 && a >= 70)
                return "CC";
            else
                return "FF";
        }
        else{
            throw new CourseNotFoundException(this,course);
        }
    }
    public String courseGradeLetter(double grade){
        if (grade >= 90 && grade <= 100)
            return "AA";
        else if (grade < 90 && grade >= 85)
            return "BA";
        else if (grade < 85 && grade >= 80)
            return "BB";
        else if (grade < 80 && grade >= 75)
            return "CB";
        else if (grade < 75 && grade >= 70)
            return "CC";
        else return "FF";
    }
    public String courseResult(Course course)
    {
        if(getCourseList().contains(course)) {
            double a = getGradeList().get(getCourseList().indexOf(course));
            if (a >= 70 && a <= 100)
                return "Passed";
            else
                return "Failed";
        }
        else{
            throw new CourseNotFoundException(this,course);
        }
    }
    public void setTeachingAssistant(Course course){
        if(teachingAssistant==null&&getCourseList().contains(course)
                && getGradeList().get(getCourseList().indexOf(course))>=80){
            this.teachingAssistant = course;
        }
        else
            throw new CourseNotFoundException(this,course);
    }
    public Course getTeachingAssistant(){
        return teachingAssistant;
    }
    public String toString(){
        return super.toString();
    }
}
class Semester{
    private int season;
    private int year;

    public Semester(int season, int year) {
        if(season>0 && season<4)
            this.season = season;
        this.year = year;
    }
    public String getSeason() {
        return switch (season) {

            case 1 -> "Fall";
            case 2 -> "Spring";
            case 3 -> "Summer";
            default -> "";
        };
    }
    public int getYear() {
        return year;
    }
    public String toString(){
        return getSeason()+" - "+ year;
    }
}
class SortSemester implements Comparator<Semester>{
    @Override
    public int compare(Semester o1, Semester o2) {
        if(o1.getYear()<o2.getYear())
            return -1;
        else if (o1.getYear()>o2.getYear())
            return 1;
        else return 0;
    }
}
class SemesterNotFoundException extends RuntimeException{
    Student student;
    Semester semester;
    public SemesterNotFoundException(Student student,Semester semester){
        this.student = student;
        this.semester = semester;
    }
    public String toString(){
        return "SemesterNotFoundException: " + student.getID() + " has not " +
                "taken any courses in " + semester;
    }
}
class CourseNotFoundException extends RuntimeException{
    private  Student student;
    private  Course course;

    public CourseNotFoundException(Student student, Course course) {
        this.student = student;
        this.course = course;
    }

    public String toString()
    {
        return "CourseNotFoundException: "+ this.student.getID()+" has not yet "
                + "taken " + this.course.courseCode();
    }
}
class DepartmentMismatchException extends RuntimeException{
    private  Department department;
    private  Teacher person;
    private  Course course;

    public DepartmentMismatchException(Course course,Teacher person) {
        this.course = course;
        this.person = person;
        this.department = null;
    }

    public DepartmentMismatchException(Department department, Teacher person) {
        this.department = department;
        this.person = person;
        this.course = null;
    }
    public String toString()
    {
        if(this.course != null)
        {
            return "DepartmentMismatchException: "+person.getName()+" ("+
                    person.getID()+") cannot teach "+course.courseCode()+
                    " because he/she is currently assigned to "+
                    person.getDepartment().getID();
        }
        else
            return "DepartmentMismatchException: "+person.getName()+" ("+
                    person.getID()+") cannot be chair of "+department.getID()+
                    " because he/she is currently assigned to "+
                    person.getDepartment().getID();
    }
}
class InvalidGradeException extends RuntimeException{
    private  double grade ;

    public InvalidGradeException(double grade) {
        this.grade = grade;
    }

    public String toString()
    {
        return "InvalidGradeException: "+ grade;
    }
}
class InvalidRankException extends RuntimeException{
    private  int rank;

    public InvalidRankException(int rank) {
        this.rank = rank;
    }

    public String toString()
    {
        return "InvalidRankException: "+ this.rank;
    }
}
class InvalidIDException extends RuntimeException{
    private  String id;
    public InvalidIDException(String id) {
        this.id = id;
    }

    public String toString()
    {
        return "InvalidIDException: Department ID "+this.id+" "+"ID Length must"+
                " be 3 or 4 characters.";
    }
}
class InvalidNumberException extends RuntimeException{
    private int number;

    public InvalidNumberException(int number) {
        this.number = number;
    }

    public String toString()
    {
        return "InvalidNumberException: Course Number "+this.number+" Number " +
                "must be between 100-499,5000-5999 or 7000-7999";
    }
}
class InvalidAKTSException extends RuntimeException{
    private  int akts;

    public InvalidAKTSException(int akts) {
        this.akts = akts;
    }
    public String toString()
    {
        return "InvalidAKTSException: Course AKTS invalid value attempted "
                +this.akts+" AKTS must be non negative";
    }
}
class InvalidEmailException extends RuntimeException{
    private  String email;
    public InvalidEmailException(String email) {
        this.email = email;
    }
    public String toString()
    {
        return "InvalidEmailException: Person Email "+this.email
                +" Email must be like 'my@example.com or my@example.edu.tr";
    }
}