/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author cekesa
 */
public class Follower {
     private Long followerId;
    private Long followedId;

    public Follower() {
    }

    public Follower(Long followerId, Long followedId) {
        this.followerId = followerId;
        this.followedId = followedId;
    }

    public Long getFollowerId() {
        return followerId;
    }

    public void setFollowerId(Long followerId) {
        this.followerId = followerId;
    }

    public Long getFollowedId() {
        return followedId;
    }

    public void setFollowedId(Long followedId) {
        this.followedId = followedId;
    }

    @Override
    public String toString() {
        return "Follower{" + "followerId=" + followerId + ", followedId=" + followedId + '}';
    }
    
    
}
