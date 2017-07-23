<?php

namespace App\Http\Controllers;

use App\Depositor;
use App\LinkAppUser;
use App\ReliefOperation;
use App\Volunteer;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Hash;

class AndroidController extends Controller
{
    public function register(Request $request) {
        $username=$request->username;
        $number=$request->number;
        $password=$request->password;

        $user=LinkAppUser::where('username', $username)->first();
        if ($user!=null) {
            return 0;
        }

        $user=new LinkAppUser();
        $user->username=$username;
        $user->number=$number;
        $user->password=Hash::make($password);
        $user->save();

        return 1;
    }

    public function login(Request $request) {
        $username=$request->username;
        $password=$request->password;

        $user=LinkAppUser::where('username', $username)->first();
        if ($user==null) {
            return 0;
        } else {
            if (Hash::check($password, $user->password)) {
                return $user->number;
            }
        }

        return 0;
    }

    public function relief_operation_list(Request $request) {
        $ros=ReliefOperation::where('id', '!=', -1)->get();
        $ros=$ros->toArray();

        return json_encode($ros);
    }

    public function get_activity_log(Request $request) {
        $user_id=LinkAppUser::where('username', $request->username)->first()->id;

        $deps=Depositor::where('user_id', '=', $user_id)->get();
        $deps=$deps->toArray();

        $vols=Volunteer::where('user_id', '=', $user_id)->get();
        $vols=$vols->toArray();

        $arr=array();
        $ctr=0;
        foreach ($deps as $dep) {
			$dep['name']=ReliefOperation::where('id', $dep['disaster_id'])->first()->name;
            $arr[$ctr]=$dep;
            $ctr++;
        }
        foreach ($vols as $vol) {
			$vol['name']=ReliefOperation::where('id', $vol['disaster_id'])->first()->name;
			$vol['amount']=-1;
            $arr[$ctr]=$vol;
            $ctr++;
        }

        for ($i=0; $i<count($arr); $i++) {
            for ($j=$i+1; $j<count($arr); $j++) {
                if ($arr[$i]['created_at'] < $arr[$j]['created_at']) {
                    $a=$arr[$i];
                    $arr[$i]=$arr[$j];
                    $arr[$j]=$a;
                }
            }
        }

        return json_encode($arr);
    }

    public function volunteer(Request $request) {
        $user_id=LinkAppUser::where('username', $request->username)->first()->id;
        $disaster_id=$request->disaster_id;

		$v=Volunteer::where('user_id', $user_id)->where('disaster_id', $disaster_id)->first();
		
		if ($v==null) {
			$v=new Volunteer();
			$v->user_id=$user_id;
			$v->disaster_id=$disaster_id;
			$v->save();

			return 1;
		}
		
		return 0;
    }

    public function deposit(Request $request) {
        $user_id=LinkAppUser::where('username', $request->username)->first()->id;
        $disaster_id=$request->disaster_id;
        $amount=$request->amount;

        $v=new Depositor();
        $v->user_id=$user_id;
        $v->disaster_id=$disaster_id;
        $v->amount=$amount;
        $v->save();

        return 1;
    }
}
