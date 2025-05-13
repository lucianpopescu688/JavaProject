package repository;
import model.Ticket;
import java.util.List;

public interface ITicketRepository
{
    void addTicket(Ticket ticket);
    List<Ticket> getTicketsByPerformanceID(int performanceID);
    List<Ticket> getAllTickets();
}