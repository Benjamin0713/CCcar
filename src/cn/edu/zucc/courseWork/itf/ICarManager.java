package cn.edu.zucc.courseWork.itf;

import cn.edu.zucc.courseWork.model.CCCar;
import cn.edu.zucc.courseWork.util.BaseException;

import java.util.List;

public interface ICarManager {
    public List<CCCar> loadAll() throws BaseException;
    public void addCar(String model, String licen, String net, String state) throws BaseException;
    public List<CCCar> loadAllCar() throws BaseException;
    public void delete(CCCar car) throws BaseException;
    public List<CCCar> loadrent() throws BaseException;
    public void changecar(String car_id, String model, String license,
                          String net,String state) throws BaseException;
    public List<CCCar> load(String net,String model) throws BaseException;
}
