package fr.miagenantes.troptardmiage.cron;

import java.io.IOException;
import java.util.logging.Logger;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.google.appengine.api.users.User;
import fr.miagenantes.troptardmiage.models.UserTtm;
import fr.miagenantes.troptardmiage.models.Event;
import fr.miagenantes.troptardmiage.repositories.UserTtmRepository;
import fr.miagenantes.troptardmiage.repositories.EventRepository;


import java.util.List;
import java.io.* ;
import java.util.*;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("serial")
public class CronServlet extends HttpServlet {
private static final Logger _logger = Logger.getLogger(CronServlet.class.getName());

public void sendmail(UserTtm user){
    try {
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

        String msgBody = "Je participe à l'evennement je le jure.";

        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress("pjboceno@gmail.com", "Admin"));
            msg.addRecipient(Message.RecipientType.TO,
               new InternetAddress(user.getUser().getEmail(), user.getUser().getNickname()));
            msg.setSubject("Es-tu un loser ?");
            msg.setText(msgBody);
            Transport.send(msg);
            _logger.info("Envoie mail");
        } catch (AddressException e) {
           // ...
        } catch (MessagingException e) {
           // ...
        }

    }
    catch (Exception ex) {
    //Log any exceptions in your Cron Job
    }
}

public void doGet(HttpServletRequest req, HttpServletResponse resp)
throws IOException {
    _logger.info("Cron Job has been executed");
    Calendar cal = Calendar.getInstance();
    Date currentDate = cal.getTime();
    // cal.add(Calendar.HOUR_OF_DAY, 1);
    // Date borderDate = cal.getTime();

    List<UserTtm> users = UserTtmRepository.users();
    for( UserTtm user : users ){
        _logger.info("users list boucle");
        Map subscriptionsUser = user.getSubscriptions();
        Set listKeys=subscriptionsUser.keySet();  // Obtenir la liste des clés
        Iterator iterateur=listKeys.iterator();
        // Parcourir les clés et afficher les entrées de chaque clé;
        while(iterateur.hasNext())
        {
            Object key= iterateur.next();
            _logger.info(key+"=>"+subscriptionsUser.get(key));
            Event eventuser = EventRepository.get(String(key));
            if(subscriptionsUser.get(key) == false && eventuser.getStartDate().compareDate(currentDate) == 0){
                CronServlet cs = new CronServlet();
                cs.sendmail(user);
            }

        }
    }

}

@Override
public void doPost(HttpServletRequest req, HttpServletResponse resp)
    throws ServletException, IOException {
        doGet(req, resp);
    }
}





// @SuppressWarnings("serial")
// public class CronServlet extends HttpServlet {

//   public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

//       Properties props = new Properties();
//         Session session = Session.getDefaultInstance(props, null);

//         String msgBody = "...";

//         try {
//             Message msg = new MimeMessage(session);
//             msg.setFrom(new InternetAddress("pjboceno@gmail.com", "Admin"));
//             msg.addRecipient(Message.RecipientType.TO,
//                              new InternetAddress("ostcommader@gmail.com", "Mr. User"));
//             msg.setSubject("Test d'envoie de mail");
//             msg.setText(msgBody);
//             Transport.send(msg);

//         } catch (AddressException e) {
//            // ...
//        } catch (MessagingException e) {
//            // ...
//     }
//   }
// }




        