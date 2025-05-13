package com.assignment.task.manager.apis.user;

import com.assignment.task.manager.model.request.CommonRequest;
import com.assignment.task.manager.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteUserApiTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private DeleteUserApi deleteUserApi;

    private CommonRequest request;

    @BeforeEach
    void setUp() {
        request = new CommonRequest();
        request.setData("user-uuid-123");
    }

    @Test
    void execute_shouldThrowException_whenUserNotFound() {
        doThrow(new RuntimeException("User not found")).when(userService).deleteById("user-uuid-123");
        RuntimeException exception = assertThrows(RuntimeException.class, () -> deleteUserApi.execute(request));
        assertEquals("User not found", exception.getMessage());
        verify(userService).deleteById("user-uuid-123");
        verifyNoMoreInteractions(userService);
    }
}
