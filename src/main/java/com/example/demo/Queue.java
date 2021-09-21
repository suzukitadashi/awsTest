package com.example.demo;

public class Queue {

	//キューの名前を定数で持つなら、グループIDも定数で持たせる。
	public static final String MESSAGE_GROUP_ID = "SBS_MESSAGE_GROUP";

	//プロパティファイル、Enum、共に @SqsListener() の引数に指定出来なかったので定数で持つしかない。
	public static final String TEST = "foo-queue.fifo";
	
}
