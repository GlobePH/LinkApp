<?php

/*
|--------------------------------------------------------------------------
| Web Routes
|--------------------------------------------------------------------------
|
| Here is where you can register web routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| contains the "web" middleware group. Now create something great!
|
*/

Route::get('/', 'MainController@index');
Route::get('/timeline', 'MainController@timeline');


Route::group(['prefix' => 'admin'], function () {
    Voyager::routes();
});


Route::group(['prefix' => 'admin'], function () {
    Voyager::routes();
});


Route::group(['prefix' => 'admin'], function () {
    Voyager::routes();
});

Route::group(['prefix' => 'android'], function() {

    Route::post('login', 'AndroidController@login');
    Route::post('register', 'AndroidController@register');
    Route::post('relief_operation_list', 'AndroidController@relief_operation_list');
    Route::post('get_activity_log', 'AndroidController@get_activity_log');
    Route::post('volunteer', 'AndroidController@volunteer');
    Route::post('deposit', 'AndroidController@deposit');
});