package android.palharini.myhealth.db.sqlite.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.palharini.myhealth.db.entities.Indicator;
import android.palharini.myhealth.db.sqlite.DbHelper;

import java.util.ArrayList;

public class IndicatorDAO extends SQLiteOpenHelper {

	private static final DbHelper dbHelper = new DbHelper();

	private static final String DATABASE = dbHelper.getDatabaseName();

	public IndicatorDAO(Context context) {
		super(context, DATABASE, null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE indicators (" +
                        "id INTEGER PRIMARY KEY," +
                        "type_id INTEGER," +
                        "user_id INTEGER," +
                        "measure_1 DOUBLE," +
                        "measure_2 DOUBLE," +
                        "timestamp INTEGER," +
                        "FOREIGN KEY (user_id) REFERENCES users(id)," +
                        "FOREIGN KEY (type_id) REFERENCES indicator_types(id)"
        );
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS indicators");
        onCreate(db);
	}

	public boolean insertIndicator(Indicator indicator) {

        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put("typeId", indicator.getTypeID());
        contentValues.put("userId", indicator.getUserID());
        contentValues.put("measure1", indicator.getMeasure1());
        contentValues.put("measure2", indicator.getMeasure2());
        // TODO: replace date/time with UNIX TIME timestamp
        contentValues.put("timestamp", indicator.getStrDate());

        database.insert("indicators", null, contentValues);

		return true;
	}
	
	public boolean updateIndicator(Indicator indicator){

		return true;
	}
	
	public boolean deleteIndicator(Integer id) {

		return true;
	}
	
	public Indicator searchIndicatorById(Integer id) {

		Indicator indicator = new Indicator();

		return indicator;
	}
	
//	public ArrayList<Indicator> selectIndicatorTypes(Integer idUsuario, Integer idTipo) {
//		Indicator indicator;
//
//		ArrayList<Indicator> indicadores = new ArrayList<>();
//
//		return indicadores;
//	}
	
	public Indicator searchIndicatorsByType (Integer userId, Integer typeId) {
		Indicator indicator = null;



		return indicator;
	}
	
	public ArrayList<Indicator> buscarIndicadoresPeriodoTipo (Integer idUsuario, Integer idTipo, String periodo, String dataAtual, Integer difData) {
		Indicator indicator = null;
		
		ArrayList<Indicator> indicadores = new ArrayList<>();

		return indicadores;
	}
	
	public Double buscarMedia1Periodo (Integer idTipo, Integer idUsuario, String periodo, String dataAtual, Integer difData) {
		
		Double media = null;

		return media;
	}
	
	public Double buscarMedia2Periodo (Integer idTipo, Integer idUsuario, String periodo, String dataAtual, Integer difData) {
		
		Double media = null;
		
		return media;
	}
}

