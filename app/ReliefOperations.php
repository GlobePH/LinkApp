<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class ReliefOperations extends Model
{
    protected $table = 'relief_operations';

    public function creatorId(){
        return $this->belongsTo(LinkAppUsers::class);
    }
}
