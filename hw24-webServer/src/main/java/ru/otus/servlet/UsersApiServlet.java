package ru.otus.servlet;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import ru.otus.crm.service.DBServiceClient;

import java.io.IOException;


@RequiredArgsConstructor
public class UsersApiServlet extends HttpServlet {


    private final DBServiceClient dbServiceClient;
    private final Gson gson;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //сохранение пользователя
    }

}
