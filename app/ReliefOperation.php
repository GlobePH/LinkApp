<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class ReliefOperation extends Model
{
    protected $table = 'relief_operations';

    public function creatorId(){
        return $this->belongsTo(LinkAppUser::class);
    }
}
