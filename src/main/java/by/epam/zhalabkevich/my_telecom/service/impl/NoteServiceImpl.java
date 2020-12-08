package by.epam.zhalabkevich.my_telecom.service.impl;

import by.epam.zhalabkevich.my_telecom.dao.DAOException;
import by.epam.zhalabkevich.my_telecom.dao.DAOProvider;
import by.epam.zhalabkevich.my_telecom.dao.NoteDAO;
import by.epam.zhalabkevich.my_telecom.service.NoteService;
import by.epam.zhalabkevich.my_telecom.service.ServiceException;

public class NoteServiceImpl implements NoteService {
    private final NoteDAO dao = DAOProvider.getInstance().getNoteDAO();

    @Override
    public boolean addTariffNote(long tariffId, long accountId) throws ServiceException {
        try {
            return dao.addNoteToAccount(tariffId, accountId);
        } catch (DAOException e) {
            throw new ServiceException("Impossible to connect (add) note to account! ");
        }
    }

    @Override
    public boolean deleteTariffNote(long noteId) throws ServiceException {
        try {
            return dao.deleteNoteFromAccount(noteId);
        } catch (DAOException e) {
           throw new ServiceException("Impossible to delete tariff note from account!");
        }
    }
}
