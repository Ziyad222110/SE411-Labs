package edu.psu.se411.model;

import static org.junit.jupiter.api.Assertions.*;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StackTest {

    private Stack<String> stringStack;

    @BeforeEach
    public void setUp() {
        stringStack = new Stack<>();
    }

    @Test
    public void push_then_pop_returns_last_pushed() {
        stringStack.push("Z");
        stringStack.push("A");
        assertEquals("A", stringStack.pop());
    }

    @Test
    public void pop_empty_stack_throws_exception() {
        NoSuchElementException thrown = assertThrows(
            NoSuchElementException.class,
            () -> stringStack.pop(),
            "Expected pop from empty Stack to throw, but it didn't"
        );
        assertTrue(thrown.getMessage().equals("Stack is empty, cannot pop"));
    }

    @Test
    public void pops_in_reverse_order() {
        stringStack.push("first");
        stringStack.push("second");
        stringStack.push("third");

        assertEquals("third", stringStack.pop());
        assertEquals("second", stringStack.pop());
        assertEquals("first", stringStack.pop());
    }
}