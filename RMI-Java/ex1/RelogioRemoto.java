import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class RelogioRemoto extends UnicastRemoteObject implements IRelogioRemoto {

    private static final String FORMATO = "EEE MMM dd HH:mm:ss zzz yyyy";

    public RelogioRemoto() throws RemoteException {
        super();
    }

    @Override
    public String dataHoraAtual() throws RemoteException {
        SimpleDateFormat formatador = new SimpleDateFormat(FORMATO, Locale.ENGLISH);
        return formatador.format(new Date());
    }
}
