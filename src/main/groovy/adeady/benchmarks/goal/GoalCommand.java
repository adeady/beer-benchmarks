package adeady.benchmarks.goal;

import adeady.benchmarks.lift.Lift;

/**
 * Created by adeady on 7/24/15.
 */
public class GoalCommand {
 public Lift getLift() {
  return lift;
 }

 public void setLift(Lift lift) {
  this.lift = lift;
 }

 public Integer getWeight() {
  return weight;
 }

 public void setWeight(Integer weight) {
  this.weight = weight;
 }

 public String getUsername() {
  return username;
 }

 public void setUsername(String username) {
  this.username = username;
 }

 private Lift lift;
 private Integer weight;
 private String username;
}
