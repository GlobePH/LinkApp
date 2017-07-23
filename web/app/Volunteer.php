<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Volunteer extends Model
{
    protected $table = 'volunteers';

    public function userId(){
        return $this->belongsTo(LinkAppUser::class);
    }

    public function disasterId(){
        return $this->belongsTo(ReliefOperation::class);
    }
}
