// This is a personal academic project. Dear PVS-Studio, please check it.

// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: https://pvs-studio.com
package test.com.example.api;

import com.example.api.Result;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResultTest {

    @Test
    void getStatusTest() {
        //Create a new Result object
        Result result = new Result(true, "Successfully deleted topology");

        //Check if the status is the same as the one we created
        assertTrue(result.getStatus());

        //Check if the result is the same as the one we created
        assertEquals(new Result(true, "Successfully deleted topology"), result);

        //Create a new Result object
        result = new Result(false, "Failed to delete topology");

        //Check if the status is the same as the one we created
        assertFalse(result.getStatus());

        //Check if the result is the same as the one we created
        assertEquals(new Result(false, "Failed to delete topology"), result);
    }

    @Test
    void getMessageTest() {
        //Create a new Result object
        Result result = new Result(true, "Successfully deleted topology");

        //Check if the message is the same as the one we created
        assertEquals("Successfully deleted topology", result.getMessage());

        //Check if the result is the same as the one we created
        assertEquals(new Result(true, "Successfully deleted topology"), result);

        //Create a new Result object
        result = new Result(false, "Failed to delete topology");

        //Check if the message is the same as the one we created
        assertEquals("Failed to delete topology", result.getMessage());

        //Check if the result is the same as the one we created
        assertEquals(new Result(false, "Failed to delete topology"), result);
    }

    @Test
    void testEqualsTest() {
        //Create a new Result object
        Result result = new Result(true, "Successfully deleted topology");

        //Check if the result is equal to itself
        assertTrue(result.equals(result));

        //Check if the result is equal to a new Result object with the same status and same message
        assertTrue(result.equals(new Result(true, "Successfully deleted topology")));

        //Check if the result is not equal to a new Result object with different status and different message
        assertFalse(result.equals(new Result(false, "Failed to delete topology")));

        //Check if the result is not equal to a new Result object with different status and the same message
        assertFalse(result.equals(new Result(true, "Failed to delete topology")));

        //Check if the result is not equal to a new Result object with the same status and different message
        assertFalse(result.equals(new Result(false, "Successfully deleted topology")));
    }

    @Test
    void testHashCodeTest() {
        //Create a new Result object
        Result result = new Result(true, "Successfully deleted topology");

        //Check if the hash code of result is the same as itself
        assertEquals(result.hashCode(), result.hashCode());

        //Check if the hash code of result is equal to a new Result object with the same status and same message
        assertEquals(result.hashCode(), new Result(true, "Successfully deleted topology").hashCode());

        //Check if the hash code of result is not equal to a new Result object with different status and different message
        assertNotEquals(result.hashCode(), new Result(false, "Failed to delete topology").hashCode());

        //Check if the hash code of result is not equal to a new Result object with different status and the same message
        assertNotEquals(result.hashCode(), new Result(true, "Failed to delete topology").hashCode());

        //Check if the hash code of result is not equal to a new Result object with the same status and different message
        assertNotEquals(result.hashCode(), new Result(false, "Successfully deleted topology").hashCode());
    }
}