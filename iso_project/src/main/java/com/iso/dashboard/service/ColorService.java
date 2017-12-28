/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.service;

import com.iso.dashboard.dao.ColorDefinedDAO;
import com.iso.dashboard.dto.CColorDefined;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author TUYENNV1
 */
public class ColorService {

    private static ColorService service;
    private Map<String, String> mapCodeColor = new HashMap<>();
    public static ColorService getInstance() {
        if (service == null) {
            service = new ColorService();
        }
        return service;
    }

    public List<CColorDefined> listOrganization() {
        return ColorDefinedDAO.getInstance().listColor();
    }
    
    public List<String> listColor(){
        mapCodeColor.clear();
        List<CColorDefined> list = listOrganization();
        List<String> listColor = new ArrayList<>();
        list.stream().forEach((ccd) -> {
            mapCodeColor.put(ccd.getColorName().toLowerCase(), ccd.getColorCode());
            listColor.add(ccd.getColorName());
        });
        return listColor;
    }

    public Map<String, String> getMapCodeColor() {
        return mapCodeColor;
    }

    public void setMapCodeColor(Map<String, String> mapCodeColor) {
        this.mapCodeColor = mapCodeColor;
    }
    
    
}
