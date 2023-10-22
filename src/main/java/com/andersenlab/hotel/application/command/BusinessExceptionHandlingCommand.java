package com.andersenlab.hotel.application.command;

import com.andersenlab.hotel.usecase.exception.ApartmentNotfoundException;
import com.andersenlab.hotel.usecase.exception.ApartmentWithSameIdExists;
import com.andersenlab.hotel.usecase.exception.ClientBannedException;
import com.andersenlab.hotel.usecase.exception.ClientIsAlreadyExistsException;
import com.andersenlab.hotel.usecase.exception.ClientNotfoundException;

import java.io.PrintStream;
import java.util.List;

public final class BusinessExceptionHandlingCommand implements Command {
    private final Command original;

    public BusinessExceptionHandlingCommand(Command original) {
        this.original = original;
    }

    @Override
    public ApplicationCommand getApplicationCommand() {
        return original.getApplicationCommand();
    }

    @Override
    public void execute(PrintStream output, List<String> arguments) {
        try {
            original.execute(output, arguments);
        } catch (ApartmentWithSameIdExists e) {
            output.println("Apartment is already exists");
        } catch (ClientIsAlreadyExistsException e) {
            output.println("Client is already exists");
        } catch (ClientNotfoundException e) {
            output.println("Client was not found");
        } catch (ApartmentNotfoundException e) {
            output.println("Apartment was not found");
        } catch (ClientBannedException e) {
            output.println("Client banned");
        }
    }

}
