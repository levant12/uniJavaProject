package ge.tsu.demoExamCalc;

import ge.tsu.demoexamcalc.controllers.BaseController;
import ge.tsu.demoexamcalc.controllers.MenuController;
import ge.tsu.demoexamcalc.controllers.ScaledController;
import ge.tsu.demoexamcalc.models.SavedResult;
import ge.tsu.demoexamcalc.models.User;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class TestClass {
    @Test
    public void testCalculation() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        BaseController baseController = new BaseController();
        Method calculateGrant1 = BaseController.class.getDeclaredMethod("calculateGrant", double.class, double.class, double.class);
        calculateGrant1.setAccessible(true);
        assertEquals(0,calculateGrant1.invoke(baseController,1,1,1));
        assertEquals(100,calculateGrant1.invoke(baseController,175,175,175));

        ScaledController scaledController = new ScaledController();
        Method calculateGrant2 = ScaledController.class.getDeclaredMethod("calculateGrant", double.class, double.class, double.class);
        calculateGrant2.setAccessible(true);
        assertEquals(0,calculateGrant2.invoke(scaledController,1,1,1));
        assertEquals(100,calculateGrant2.invoke(scaledController,175,175,175));
    }

    @Test
    public void testSave() throws SQLException, NoSuchFieldException, IllegalAccessException {
        User user = new User();
        user.setID(1);
        SavedResult savedResult = new SavedResult();
        savedResult.setGeoP(45.0);
        savedResult.setfLangP(45.0);
        savedResult.setThirdSubjectP(45.0);
        savedResult.setThirdSubject("Math");
        savedResult.setCalculated(4500.0);
        savedResult.setGrant(100);
        savedResult.setUserID(user.getID());
        assertEquals(1, MenuController.saveResult(savedResult,user));
    }
}
