법package jpql.main;

import jpql.Member;

import javax.persistence.*;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try {
            Member member = new Member();
            member.setUsername("member1");
            member.setAge(10);
            em.persist(member);

            // TypeQuery : 반환 타입이 명확할 때 사용
            // Query : 반환 타입이 명확하지 않을 때 사
//            TypedQuery<Member> query1 = em.createQuery("select m from Member m", Member.class);

//            TypedQuery<String> query2 = em.createQuery("select m.username from Member m", String.class);
//            Query query3 = em.createQuery("select m.username, m.age from Member m");

            //query.getResultList() : 결과가 하나 이상일때, 리스트 반환. 결과가 없으면 빈 리스트 반환
            //query.getSingleResult() : 결과가 정확하나 , 단일객체 반환
            // - 결과가 없으면 : NoResultException
            // - 결과가 둘 이상 : NonUniqueResultException
//            List<Member> resultList = query1.getResultList();
//            Member singleResult = query1.getSingleResult();

            Member singleResult = em.createQuery("select m from Member m where m.username = :username", Member.class)
                    .setParameter("username", "member1")
                    .getSingleResult();

            System.out.println("singleResult = " + singleResult.getUsername());


            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
        emf.close();
    }
}
