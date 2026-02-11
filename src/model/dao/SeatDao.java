package model.dao;

import model.dto.SeatDto;

import java.util.ArrayList;

public interface SeatDao {
    boolean addSeats();
    boolean updateSeats();
    boolean deleteSeats();
    ArrayList<SeatDto> getSeats();
}
