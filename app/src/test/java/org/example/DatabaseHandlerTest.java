package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.*;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseHandlerTest {

    private DatabaseHandler dbHandler;
    private Connection mockConnection;
    private PreparedStatement mockPreparedStatement;
    private ResultSet mockResultSet;

    @BeforeEach
    void setUp() {
        // Mock the Connection, PreparedStatement, and ResultSet
        mockConnection = mock(Connection.class);
        mockPreparedStatement = mock(PreparedStatement.class);
        mockResultSet = mock(ResultSet.class);

        // Initialize the DatabaseHandler with the mock connection
        dbHandler = new DatabaseHandler(mockConnection);
    }

    @Test
    void testGetAllStudents() throws SQLException {
        // Mock the behavior of the result set
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockResultSet.getString("student_name")).thenReturn("John Doe");
        when(mockResultSet.getString("student_id")).thenReturn("S123");

        // Call the method and verify the result
        ResultSet resultSet = dbHandler.getAllStudents();
        assertNotNull(resultSet);

        // Verify interactions
        verify(mockPreparedStatement, times(1)).executeQuery();
    }

    @Test
    void testSearchStudentByField() throws SQLException {
        // Mock behavior of ResultSet for search
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockResultSet.getString("student_name")).thenReturn("Jane Doe");
        when(mockResultSet.getString("student_id")).thenReturn("S124");

        ResultSet resultSet = dbHandler.searchStudentByField("student_name", "Jane");
        assertNotNull(resultSet);
        verify(mockPreparedStatement, times(1)).executeQuery();
    }

    @Test
    void testSearchStudentInvalidField() throws SQLException {
        // Test when an invalid field is provided
        SQLException thrown = assertThrows(SQLException.class, () -> {
            dbHandler.searchStudentByField("invalid_field", "Invalid");
        });

        assertEquals("Unknown column 'invalid_field' in 'field list'", thrown.getMessage());
    }
}
