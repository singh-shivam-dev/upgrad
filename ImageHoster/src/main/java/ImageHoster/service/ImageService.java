package ImageHoster.repository;

import ImageHoster.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.*;

//The annotation is a special type of @Component annotation which describes that the class defines a data repository
@Repository
public class UserRepository {
    //Get an instance of EntityManagerFactory from persistence unit with name as 'imageHoster'
    @PersistenceUnit(unitName = "imageHoster")
    private EntityManagerFactory emf;

    //The method receives the User object to be persisted in the database
    //Creates an instance of EntityManager
    //Starts a transaction
    //The transaction is committed if it is successful
    //The transaction is rolled back in case of unsuccessful transaction
    public void registerUser(User newUser) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            //persist() method changes the state of the model object from transient state to persistence state
            em.persist(newUser);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        }
    }


    //The method receives the entered username and password
    //Creates an instance of EntityManager
    //Executes JPQL query to fetch the user from User class where username is equal to received username and password is equal to received password
    //Returns the fetched user
    //Returns null in case of NoResultException
    public User checkUser(String username, String password) {
        try {
            EntityManager em = emf.createEntityManager();
            TypedQuery<User> typedQuery = em.createQuery("SELECT u FROM User u WHERE u.username = :username AND u.password = :password", User.class);
            typedQuery.setParameter("username", username);
            typedQuery.setParameter("password", password);

            return typedQuery.getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }
}package ImageHoster.service;

import ImageHoster.model.Comment;
import ImageHoster.model.Image;
import ImageHoster.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ImageService {
    @Autowired
    private ImageRepository imageRepository;

    //Call the getAllImages() method in the Repository and obtain a List of all the images in the database
    public List<Image> getAllImages() {
        return imageRepository.getAllImages();
    }


    //The method calls the createImage() method in the Repository and passes the image to be persisted in the database
    public void uploadImage(Image image) {
        imageRepository.uploadImage(image);
    }


    //The method calls the getImageByTitle() method in the Repository and passes the title of the image to be fetched
    public Image getImageById(int id) {
        return imageRepository.getImageById(id);
    }

    //The method calls the getImage() method in the Repository and passes the id of the image to be fetched
    public Image getImage(Integer imageId) {
        return imageRepository.getImage(imageId);
    }

    //The method calls the updateImage() method in the Repository and passes the Image to be updated in the database
    public void updateImage(Image updatedImage) {
        imageRepository.updateImage(updatedImage);
    }

    //The method calls the deleteImage() method in the Repository and passes the Image id of the image to be deleted in the database
    public void deleteImage(Integer imageId) {
        imageRepository.deleteImage(imageId);
    }


    public void saveComment(Comment comment) {
        imageRepository.saveComment(comment);
    }

}
