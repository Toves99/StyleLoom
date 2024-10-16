/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package service;
import java.sql.SQLException;
import java.util.List;
import model.Follower;
/**
 *
 * @author cekesa
 */
public interface FollowerService {
    void follow(Follower follower) throws SQLException;
    void unfollow(Long followerId, Long followedId) throws SQLException;
    List<Follower> getFollowers(Long followedId) throws SQLException;
    List<Follower> getFollowing(Long followerId) throws SQLException;
    boolean isFollowing(Long followerId, Long followedId) throws SQLException;
}
