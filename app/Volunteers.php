<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Volunteers extends Model
{
    protected $table = 'volunteers';

    public function userId(){
        return $this->belongsTo(LinkAppUsers::class);
    }

    public function disasterId(){
        return $this->belongsTo(ReliefOperations::class);
    }
}
