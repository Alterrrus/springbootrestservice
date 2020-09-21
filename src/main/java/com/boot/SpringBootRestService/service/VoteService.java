package com.boot.SpringBootRestService.service;


import com.boot.SpringBootRestService.model.Vote;
import com.boot.SpringBootRestService.repository.JPA.VoteRepo;

import com.boot.SpringBootRestService.util.exception.DoubleVoteException;
import com.boot.SpringBootRestService.web.SecurityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static com.boot.SpringBootRestService.util.ValidationUtil.checkNotFoundWithId;


@Service
public class VoteService extends AbstractService {
    protected final Logger log = LoggerFactory.getLogger(getClass());
    private final VoteRepo voteRepo;

    public VoteService(VoteRepo voteRepo) {
        this.voteRepo = voteRepo;
    }

    public List<Vote> getByTimeExist(int userId) {
        LocalDate dateTime = LocalDate.now();
        return voteRepo.getByTimeExist(userId, dateTime.atStartOfDay(), dateTime.atTime(23, 59, 59));
    }

    public boolean delete(int id, int restaurantId, int userId) {
        return voteRepo.delete(id, restaurantId, userId);
    }

    public Vote get(int id, int restaurantId, int userId) {
        return checkNotFoundWithId(voteRepo.get(id, restaurantId, userId),id);

    }

    public Vote save(int restaurantId) {
        int userId= SecurityUtil.authUserId();
        Vote base=voteRepo.getByTimeToDay(userId);

        if(base==null){
            Vote v=voteRepo.save(new Vote(userId,restaurantId),userId,restaurantId);
            log.info("new vote created "+ "id: "+v.id()+"\n"+"restaurantId: "+v.getRestaurantId()+
                    "\n"+"userId: "+v.getUserId());
            return v;}
        else
            if(base.getTimeExist().isAfter(LocalDate.now().atStartOfDay())
                    &&base.getTimeExist().isBefore(LocalDate.now().atTime(11,00,00))){
               voteRepo.update(restaurantId,base.id(),userId);
               Vote v1=voteRepo.getByTimeToDay(userId);
                log.info("vote updated "+ "id: "+v1.id()+"\n"+"restaurantId: "+v1.getRestaurantId()+
                        "\n"+"userId: "+v1.getUserId());
               return v1;
            }
            else throw new DoubleVoteException("can not update vote id:" +base.id());
               /* log.info("voting is not possible "+ "id: "+base.id()+"\n"+"restaurantId: "+base.getRestaurantId()+
                        "\n"+"userId: "+base.getUserId());
                //checkNotFound(null,"сменить ваше нешение можно с 00 до 11");
                return base;*/
    }

    public List<Vote> getAll(int restaurantId) {
        return voteRepo.getAll(restaurantId);
    }

}
