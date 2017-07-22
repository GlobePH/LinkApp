<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Depositors extends Model
{
    protected $table = 'depositors';

    public function userId(){
        return $this->belongsTo(LinkAppUsers::class);
    }

    public function disasterId(){
        return $this->belongsTo(ReliefOperations::class);
    }
}
