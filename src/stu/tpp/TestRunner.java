package stu.tpp;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class TestRunner {
    public static void main(String[] args) throws ClassNotFoundException {

        if (args.length != 1) {
            throw new IllegalArgumentException("Exactly 1 argument must be provided");
        }
        Class<?> testClass = Class.forName(args[0]);
        String result = "Class " + testClass + " successfully loaded\n";
        Method[] declaredMethods = testClass.getDeclaredMethods();

        int successful = 0;
        int testCount = 0;

        for (Method declaredMethod : declaredMethods) {
            String nameMethod = declaredMethod.getName();
            if (nameMethod.indexOf("test") == 0) {
                result += "\tTest: " + nameMethod;
            } else {
                result += "\tMethod: " + nameMethod + " is not test method\n";
                continue;
            }
            if (declaredMethod.getModifiers() != Modifier.PUBLIC) {
                result += " is not public method\n";
                continue;
            } else if (declaredMethod.getParameterCount() != 0) {
                result += " method takes parameters\n";
                continue;
            } else if (!declaredMethod.getReturnType().toString().equals("void")) {
                result += " is not void method\n";
                continue;
            }

            testCount++;

            try {
                CalculatorTest obj = (CalculatorTest) testClass.getConstructor().newInstance();
                Method mth = obj.getClass().getDeclaredMethod(nameMethod);
                mth.invoke(obj);
                result += " SUCCESSFUL\n";
                successful++;
            } catch (Exception e) {
                result += " FAILED, error: " + e.getCause().getMessage() + "\n";
            }

        }
        result += "\tTotal methods: " + declaredMethods.length + " Total tests: " + testCount
                + " Successful: " + successful + " Failed: " + (testCount - successful);
        System.out.println(result);

    }
}
