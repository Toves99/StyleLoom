/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.serviceimpl;

import Utils.DatabaseConnect;
import Utils.Queries;
import java.sql.SQLException;
import java.util.List;
import model.Follower;
import service.FollowerService;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.sql.ResultSet;

/**
 *
 * @author cekesa
 */
public class FollowerServiceImpl implements FollowerService {

    @Override
    public void follow(Follower follower) throws SQLException {
        try (Connection conn = DatabaseConnect.getConnection(); PreparedStatement stmt = conn.prepareStatement(Queries.INSERT_FOLLOWS)) {
            stmt.setLong(1, follower.getFollowedId());
            stmt.setLong(2, follower.getFollowerId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error while trying to follow user.");
        }
    }

    @Override
    public void unfollow(Long followerId, Long followedId) throws SQLException {
        try (Connection conn = DatabaseConnect.getConnection(); PreparedStatement stmt = conn.prepareStatement(Queries.UNFOLLOW)) {
            stmt.setLong(1, followerId);
            stmt.setLong(2, followedId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error while trying to unfollow user.");
        }
    }

    @Override
    public List<Follower> getFollowers(Long followedId) throws SQLException {
        List<Follower> followers = new ArrayList<>();
        try (Connection conn = DatabaseConnect.getConnection(); PreparedStatement stmt = conn.prepareStatement(Queries.SELECT_FOLLOWERS)) {
            stmt.setLong(1, followedId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                followers.add(new Follower(rs.getLong("followerId"), rs.getLong("followedId")));
            }
        } catch (SQLException e) {
            throw new SQLException("Error while trying to Followers.");
        }
        return followers;
    }

    @Override
    public List<Follower> getFollowing(Long followerId) throws SQLException {
        List<Follower> following = new ArrayList<>();
        try (Connection conn = DatabaseConnect.getConnection(); PreparedStatement stmt = conn.prepareStatement(Queries.SELECT_FOLLOWING)) {
            stmt.setLong(1, followerId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                following.add(new Follower(rs.getLong("followerId"), rs.getLong("followedId")));
            }

        } catch (SQLException e) {
            throw new SQLException("Error while fetching users followed by user.");
        }
        return following;
    }

    @Override
    public boolean isFollowing(Long followerId, Long followedId) throws SQLException {
        try (Connection conn = DatabaseConnect.getConnection(); PreparedStatement stmt = conn.prepareStatement(Queries.SELECT_ISFOLLOWING)) {
            stmt.setLong(1, followerId);
            stmt.setLong(2, followedId);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            throw new SQLException("Error while checking if user.");
        }
        
    }

}
