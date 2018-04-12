package com.alissonrubim.fifaexpress.Helper;

import com.alissonrubim.fifaexpress.Model.Team;
import com.alissonrubim.fifaexpress.R;

/**
 * Created by alissonrubim on 12/04/2018.
 */

public class TeamLogoCatcher {
    public static int GetLogo(Team team){
        String name = team.getName();

        if(name.equals("Flamengo"))
            return R.drawable.logo_flamengo;
        else if(name.equals("Santos"))
            return R.drawable.logo_santos;
        else if(name.equals("Barcelona"))
            return R.drawable.logo_barcelona;
        else if(name.equals("Vasco"))
            return R.drawable.logo_vasco;
        else if(name.equals("Tupi"))
            return R.drawable.logo_tupi;
        else if(name.equals("Brasil"))
            return R.drawable.logo_brasil;
        else
            return R.drawable.logo_none;

    }
}
