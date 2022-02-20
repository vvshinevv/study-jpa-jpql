package book.jpql;

import book.jpql.domain.Member;
import book.jpql.domain.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JPQLMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpql");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {
            Team teamA = new Team("TeamA");
            em.persist(teamA);

            Team teamB = new Team("TeamB");
            em.persist(teamB);

            Member member1 = new Member("member1", 11, teamA);
            Member member2 = new Member("member2", 12, teamA);
            Member member3 = new Member("member3", 13, teamB);
            Member member4 = new Member("member4", 14, null);

            em.persist(member1);
            em.persist(member2);
            em.persist(member3);
            em.persist(member4);

            em.flush();
            em.clear();

            String query = "select m from Member m";
            List<Member> results = em.createQuery(query, Member.class).getResultList();

            for (Member result : results) {
                System.out.println("result : " + result.getUsername() + " " + result.getTeam().getName());
            }

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
            emf.close();
        }
    }
}
