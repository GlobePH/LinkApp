<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Depositor extends Model
{
    protected $table = 'depositors';

    public function userId(){
        return $this->belongsTo(LinkAppUser::class);
    }

    public function disasterId(){
        return $this->belongsTo(ReliefOperation::class);
    }
}
