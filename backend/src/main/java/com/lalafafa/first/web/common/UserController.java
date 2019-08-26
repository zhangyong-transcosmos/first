package com.lalafafa.first.web.common;

import com.lalafafa.first.core.Result;
import com.lalafafa.first.core.ResultGenerator;
import com.lalafafa.first.model.User;
import com.lalafafa.first.service.common.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {
    @Resource
    private UserService userService;
    
    @GetMapping("/info")
    public Result info() {
    	User userInfo = this.userService.getUserInfoByToken();
    	return ResultGenerator.genSuccessResult(userInfo);
    }

    @PostMapping
    public Result add(@RequestBody User user) {
        userService.save(user);
        return ResultGenerator.genSuccessResult();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable String id) {
        userService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @PutMapping
    public Result update(@RequestBody User user) {
        userService.update(user);
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable String id) {
        User user = userService.findById(id);
        return ResultGenerator.genSuccessResult(user);
    }

    @GetMapping
    public Result list(@RequestParam(defaultValue = "0") Integer page,
    					@RequestParam(defaultValue = "0") Integer limit,
    					@RequestParam Map<String, Object> params) {
        PageHelper.startPage(page, limit);
        List<User> list = userService.findAll();
        //List<Map<String, Object>> list = userService.queryDataList(params);
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
