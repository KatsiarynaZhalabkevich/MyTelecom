package by.epam.zhalabkevich.my_telecom.dao;

import by.epam.zhalabkevich.my_telecom.bean.Note;

public interface NoteDAO {
    boolean addNoteToAccount(long tariffId, long accountId) throws DAOException;

    boolean deleteNoteFromAccount(long noteId) throws DAOException;
}
