package by.epam.zhalabkevich.my_telecom.service;

public interface NoteService {
    boolean addTariffNote(long tariffId, long accountId) throws ServiceException;

    boolean deleteTariffNote(long noteId) throws ServiceException;
}
