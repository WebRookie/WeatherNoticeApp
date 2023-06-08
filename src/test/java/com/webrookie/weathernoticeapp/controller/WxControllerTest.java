package com.webrookie.weathernoticeapp.controller;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author WebRookie
 * @date 2023/6/8 11:03
 **/
class WxControllerTest {

    @Test
    void hello() {
        int[] demo = new int [] {0, 1, 0, 12, 3};
        moveZeros(demo);
        System.out.println(Arrays.toString(demo));
    }
    public void moveZeros(int[] nums) {
        int idx = 0;
        for(int num: nums) {
            if(num != 0) {
                nums[idx++] = num;
            }
        }
        while (idx < nums.length) {
            nums[idx++] = 0;
        }
    }
}