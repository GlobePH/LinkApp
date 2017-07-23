<?php

namespace App\Http\Controllers;

use App\ReliefOperation;
use Illuminate\Http\Request;

class MainController extends Controller
{
    public function index() {
        $data=array();
        
        $data['evacuations']=ReliefOperation::all();
        
        return view('welcome', $data);
    }

    public function timeline() {
    	return view('timeline');
    }
}
