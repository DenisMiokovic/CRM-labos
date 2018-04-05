package hr.java.vjezbe.baza.podataka;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import hr.java.vjezbe.entitet.Alarm;
import hr.java.vjezbe.entitet.Artikl;
import hr.java.vjezbe.entitet.KategorijaArtikla;
import hr.java.vjezbe.entitet.Klijent;
import hr.java.vjezbe.entitet.Komunikacija;
import hr.java.vjezbe.entitet.Tvrtka;
import hr.java.vjezbe.entitet.Usluga;
import hr.java.vjezbe.entitet.VrstaKomunikacije;
import hr.java.vjezbe.entitet.VrstaUsluge;
import hr.java.vjezbe.entitet.Zaposlenik;

public class BazaPodataka {

	private static final String DATABASE_FILE = "bazaPodataka.properties";
	
	private static Connection spajanjeNaBazuPodataka() throws SQLException, IOException {
		
		Properties svojstva = new Properties();
		
		svojstva.load(new FileReader(DATABASE_FILE));
		
		String bazaPodatakaUrl = svojstva.getProperty("bazaPodatakaUrl");
		String korisnickoIme = svojstva.getProperty("korisnickoIme");
		String lozinka = svojstva.getProperty("lozinka");
		
		Connection vezaNaBazu = DriverManager.getConnection(bazaPodatakaUrl, korisnickoIme, lozinka);
		
		return vezaNaBazu;
	}
	
	private static void zatvaranjeVezeNaBazuPodataka(Connection vezaNaBazu) throws SQLException {
		
		vezaNaBazu.close();
	}
	
	public static void spremiKlijenta(Klijent klijent, Tvrtka tvrtka) throws SQLException, IOException {
		
		Connection veza = spajanjeNaBazuPodataka();
		
		veza.setAutoCommit(false);
		
		try {
			
			PreparedStatement insertKlijent = veza.prepareStatement("INSERT INTO CRM.KLIJENT (OIB, PREZIME, IME, TELEFON, EMAIL, DATUM_RODJENJA) VALUES (?, ?, ?, ?, ?, ?);");
			insertKlijent.setString(1, klijent.getOib());
			insertKlijent.setString(2, klijent.getPrezime());
			insertKlijent.setString(3, klijent.getIme());
			insertKlijent.setString(4, klijent.getBrojTelefona());
			insertKlijent.setString(5, klijent.getEMailAdresa());
			insertKlijent.setDate(6, Date.valueOf(klijent.getDatumRodjenja()));
			
			insertKlijent.executeUpdate();
			
			ResultSet generatedKeys = insertKlijent.getGeneratedKeys();
			
			if (generatedKeys.next()) {
				
				klijent.setId(generatedKeys.getInt(1));
			}
			
			PreparedStatement insertTvrtkaKlijent = veza.prepareStatement("INSERT INTO CRM.TVRTKA_KLIJENT VALUES (?, ?)");
			insertTvrtkaKlijent.setInt(1, 1);
			insertTvrtkaKlijent.setInt(2, klijent.getId());
			
			insertTvrtkaKlijent.executeUpdate();
			
			veza.commit();
		} catch (Throwable ex) {
			veza.rollback();
			throw ex;
		}
		
		zatvaranjeVezeNaBazuPodataka(veza);
	}
	
	public static void spremiZaposlenika(Zaposlenik zaposlenik, Tvrtka tvrtka) throws SQLException, IOException {
		
		Connection veza = spajanjeNaBazuPodataka();
		
		veza.setAutoCommit(false);
		
		try {
			
			PreparedStatement insertZaposlenik = veza.prepareStatement("INSERT INTO CRM.ZAPOSLENIK (IME, PREZIME, KORISNICKO_IME, SIFRA) VALUES (?, ?, ?, ?);");
			insertZaposlenik.setString(1, zaposlenik.getIme());
			insertZaposlenik.setString(2, zaposlenik.getPrezime());
			insertZaposlenik.setString(3, zaposlenik.getKorisnickoIme());
			insertZaposlenik.setString(4, zaposlenik.getSifraZaposlenika());
			
			insertZaposlenik.executeUpdate();
			
			ResultSet generatedKeys = insertZaposlenik.getGeneratedKeys();
			
			if (generatedKeys.next()) {
				
				zaposlenik.setId(generatedKeys.getInt(1));
			}
			
			PreparedStatement insertTvrtkaZaposlenik = veza.prepareStatement("INSERT INTO CRM.TVRTKA_ZAPOSLENIK VALUES (?, ?)");
			insertTvrtkaZaposlenik.setInt(1, 1);
			insertTvrtkaZaposlenik.setInt(2, zaposlenik.getId());
			
			insertTvrtkaZaposlenik.executeUpdate();
			
			veza.commit();
		} catch (Throwable ex) {
			veza.rollback();
			throw ex;
		}
		
		zatvaranjeVezeNaBazuPodataka(veza);
	}
	
	public static void spremiArtikl(Artikl artikl, Tvrtka tvrtka) throws SQLException, IOException {
		
		Connection veza = spajanjeNaBazuPodataka();
		
		veza.setAutoCommit(false);
		
		try {
			
			PreparedStatement insertArtikl = veza.prepareStatement("INSERT INTO CRM.ARTIKL (NAZIV, KATEGORIJA_ID) VALUES (?, ?);");
			insertArtikl.setString(1, artikl.getNaziv());
			
			String kategorijaString = artikl.getKategorija().toString();
			Integer brojKategorije = null;
			
			if (kategorijaString.equals("SOFTVER")) {
				brojKategorije = 1;
			}
			if (kategorijaString.equals("ELEKTROTEHNIKA")) {
				brojKategorije = 2;
			}
			if (kategorijaString.equals("MEHANIKA")) {
				brojKategorije = 3;
			}
			if (kategorijaString.equals("OSTALO")) {
				brojKategorije = 4;
			}
			
			insertArtikl.setInt(2, brojKategorije);
			
			insertArtikl.executeUpdate();
			
			ResultSet generatedKeys = insertArtikl.getGeneratedKeys();
			
			if (generatedKeys.next()) {
				
				artikl.setId(generatedKeys.getInt(1));
			}
			
			PreparedStatement insertTvrtkaArtikl = veza.prepareStatement("INSERT INTO CRM.TVRTKA_ARTIKL VALUES (?, ?)");
			insertTvrtkaArtikl.setInt(1, 1);
			insertTvrtkaArtikl.setInt(2, artikl.getId());
			
			insertTvrtkaArtikl.executeUpdate();
			
			veza.commit();
		} catch (Throwable ex) {
			veza.rollback();
			throw ex;
		}
		
		zatvaranjeVezeNaBazuPodataka(veza);
	}
	
	public static void spremiAlarm(Alarm alarm) throws SQLException, IOException {
		
		Connection veza = spajanjeNaBazuPodataka();
		
		veza.setAutoCommit(false);
		
		try {
			
			PreparedStatement insertAlarm = veza.prepareStatement("INSERT INTO CRM.ALARM (OPIS, KLIJENT_ID, VRIJEME, AKTIVAN) VALUES (?, ?, ?, ?);");
			insertAlarm.setString(1, alarm.getOpisAlarma());
			String klijentString = alarm.getKlijent().getId().toString();
			insertAlarm.setString(2, klijentString);
			insertAlarm.setString(3, alarm.getVrijemeAlarma().toString());
			insertAlarm.setBoolean(4, alarm.isStatusAlarma());
			
			insertAlarm.executeUpdate();
			
			ResultSet generatedKeys = insertAlarm.getGeneratedKeys();
			
			if (generatedKeys.next()) {
				
				alarm.setId(generatedKeys.getInt(1));
			}
			
		
			veza.commit();
		} catch (Throwable ex) {
			veza.rollback();
			throw ex;
		}
		
		zatvaranjeVezeNaBazuPodataka(veza);
	}
	
	public static void spremiKomunikaciju(Komunikacija komunikacija) throws SQLException, IOException {
		
		Connection veza = spajanjeNaBazuPodataka();
		
		veza.setAutoCommit(false);
		
		try {
			
			PreparedStatement insertKomunikaciju = veza.prepareStatement("INSERT INTO CRM.KOMUNIKACIJA (KLIJENT_ID, ZAPOSLENIK_ID, VRSTA_KOMUNIKACIJE_ID, SADRZAJ, VRIJEME) VALUES (?, ?, ?, ?, ?);");
			insertKomunikaciju.setString(1, komunikacija.getKlijent().getId().toString());
			insertKomunikaciju.setString(2, komunikacija.getZaposlenik().getId().toString());
			insertKomunikaciju.setString(3, komunikacija.getVrstaKomunikacije().getId().toString());
			insertKomunikaciju.setString(4, komunikacija.getSadrzajKomunikacije());
			insertKomunikaciju.setString(5, komunikacija.getVrijemeKomunikacije().toString());
			
			insertKomunikaciju.executeUpdate();
			
			ResultSet generatedKeys = insertKomunikaciju.getGeneratedKeys();
			
			if (generatedKeys.next()) {
				
				komunikacija.setId(generatedKeys.getInt(1));
			}
			
		
			veza.commit();
		} catch (Throwable ex) {
			veza.rollback();
			throw ex;
		}
		
		zatvaranjeVezeNaBazuPodataka(veza);
	}
	
	public static void spremiUslugu(Usluga usluga) throws SQLException, IOException {
		
		Connection veza = spajanjeNaBazuPodataka();
		
		veza.setAutoCommit(false);
		
		try {
			
			PreparedStatement insertUslugu = veza.prepareStatement("INSERT INTO CRM.USLUGA (OPIS, KLIJENT_ID, VRSTA_USLUGE_ID, DATUM, CIJENA, PLACENA, OBAVLJENA) VALUES (?, ?, ?, ?, ?, ?, ?);");
			insertUslugu.setString(1, usluga.getOpisUsluge());
			insertUslugu.setString(2, usluga.getKlijent().getId().toString());
			insertUslugu.setString(3, usluga.getVrstaUsluge().getId().toString());
			insertUslugu.setDate(4, Date.valueOf(usluga.getDatumUsluge()));
			insertUslugu.setBigDecimal(5, usluga.getCijenaUsluge());
			insertUslugu.setBoolean(6, usluga.isUslugaNaplacena());
			insertUslugu.setBoolean(7, usluga.isUslugaObavljena());
			
			insertUslugu.executeUpdate();
			
			ResultSet generatedKeys = insertUslugu.getGeneratedKeys();
			
			if (generatedKeys.next()) {
				
				usluga.setId(generatedKeys.getInt(1));
			}
			
		
			veza.commit();
		} catch (Throwable ex) {
			veza.rollback();
			throw ex;
		}
		
		zatvaranjeVezeNaBazuPodataka(veza);
	}
	
	public static List<Klijent> dohvatiKlijente() throws SQLException, IOException {
		
		Connection veza = spajanjeNaBazuPodataka();
		
		Statement statementKlijenti = veza.createStatement();
		ResultSet resultSetKlijenti = statementKlijenti.executeQuery("SELECT * FROM CRM.KLIJENT");
		List<Klijent> listaKlijenata = new ArrayList<>();
		
		while (resultSetKlijenti.next()) {
			
			Integer klijentId = resultSetKlijenti.getInt("ID");
			String oib = resultSetKlijenti.getString("OIB");
			String prezime = resultSetKlijenti.getString("PREZIME");
			String ime = resultSetKlijenti.getString("IME");
			String brojTelefona = resultSetKlijenti.getString("TELEFON");
			String eMailAdresa = resultSetKlijenti.getString("EMAIL");
			LocalDate datumRodjenja = resultSetKlijenti.getDate("DATUM_RODJENJA").toLocalDate();
			
			Klijent klijent = new Klijent(ime, prezime, oib, brojTelefona, eMailAdresa, datumRodjenja);
			klijent.setId(klijentId);
			
			listaKlijenata.add(klijent);
		}
		
		zatvaranjeVezeNaBazuPodataka(veza);
		
		return listaKlijenata;
	}
	
	public static List<Zaposlenik> dohvatiZaposlenike() throws SQLException, IOException {
		
		Connection veza = spajanjeNaBazuPodataka();
		
		Statement statementZaposlenici = veza.createStatement();
		ResultSet resultSetZaposlenici = statementZaposlenici.executeQuery("SELECT * FROM CRM.ZAPOSLENIK");
		List<Zaposlenik> listaZaposlenika = new ArrayList<>();
		
		while (resultSetZaposlenici.next()) {
			
			Integer zaposlenikId = resultSetZaposlenici.getInt("ID");
			String prezime = resultSetZaposlenici.getString("PREZIME");
			String ime = resultSetZaposlenici.getString("IME");
			String korisnickoIme = resultSetZaposlenici.getString("KORISNICKO_IME");
			String sifraZaposlenika = resultSetZaposlenici.getString("SIFRA");
			
			Zaposlenik zaposlenik = new Zaposlenik(ime, prezime, korisnickoIme, sifraZaposlenika);
			zaposlenik.setId(zaposlenikId);
			
			listaZaposlenika.add(zaposlenik);
		}
		
		zatvaranjeVezeNaBazuPodataka(veza);
		
		return listaZaposlenika;
	}

	public static List<Artikl> dohvatiArtikle() throws SQLException, IOException {
		
		Connection veza = spajanjeNaBazuPodataka();
		
		Statement statementArtikli = veza.createStatement();
		Statement statementKategorija = veza.createStatement();
		ResultSet resultSetArtikli = statementArtikli.executeQuery("SELECT * FROM CRM.ARTIKL");
		List<Artikl> listaArtikala = new ArrayList<>();
		
		while (resultSetArtikli.next()) {
			
			Integer artiklId = resultSetArtikli.getInt("ID");
			String naziv = resultSetArtikli.getString("NAZIV");
			String kategorijaString = resultSetArtikli.getString("KATEGORIJA_ID");
			
			ResultSet resultSetKategorija = statementKategorija.executeQuery("SELECT NAZIV FROM CRM.KATEGORIJA_ARTIKLA WHERE ID IS " + kategorijaString);
			
			String kategorijaStringN = null;
			
			if (resultSetKategorija.next()) { 
				 kategorijaStringN = resultSetKategorija.getString("NAZIV");
				}

			KategorijaArtikla kategorija = KategorijaArtikla.valueOf(kategorijaStringN);
			
			Artikl artikl = new Artikl(naziv, kategorija);
			artikl.setId(artiklId);
			
			listaArtikala.add(artikl);
		}
		
		zatvaranjeVezeNaBazuPodataka(veza);
		
		return listaArtikala;
	}
	
	public static List<Alarm> dohvatiAlarme(String querry) throws SQLException, IOException {
		
		Connection veza = spajanjeNaBazuPodataka();
		
		Statement statementAlarmi = veza.createStatement();
		Statement statementKlijenti = veza.createStatement();
		ResultSet resultSetAlarmi = statementAlarmi.executeQuery(querry);
		List<Alarm> listaAlarma = new ArrayList<>();
		
		while (resultSetAlarmi.next()) {
			
			Integer alarmId = resultSetAlarmi.getInt("ID");
			String klijentString = resultSetAlarmi.getString("KLIJENT_ID");
			
			ResultSet resultSetKlijent = statementKlijenti.executeQuery("SELECT * FROM CRM.KLIJENT WHERE ID IS " + klijentString);
			Integer klijentId = null;
			String ime = null;
			String prezime = null;
			String oib = null;
			String brojTelefona = null;
			String eMailAdresa = null;
			LocalDate datumRodjenja = null;
			if (resultSetKlijent.next()) { 
				klijentId = resultSetKlijent.getInt("ID");
				ime = resultSetKlijent.getString("IME");
				prezime = resultSetKlijent.getString("PREZIME");
				oib = resultSetKlijent.getString("OIB");
				brojTelefona = resultSetKlijent.getString("TELEFON");
				eMailAdresa = resultSetKlijent.getString("EMAIL");
				datumRodjenja = resultSetKlijent.getDate("DATUM_RODJENJA").toLocalDate();
				}
			Klijent klijent = new Klijent(ime, prezime, oib, brojTelefona, eMailAdresa, datumRodjenja);
			klijent.setId(klijentId);
			
			String opisAlarma = resultSetAlarmi.getString("OPIS");
			LocalDateTime vrijemeAlarma = resultSetAlarmi.getTimestamp("VRIJEME").toLocalDateTime();		
			Boolean statusAlarma = resultSetAlarmi.getBoolean("AKTIVAN");
			
			Alarm alarm = new Alarm(klijent, opisAlarma, vrijemeAlarma, statusAlarma);
			alarm.setId(alarmId);
			
			listaAlarma.add(alarm);
		}
		
		zatvaranjeVezeNaBazuPodataka(veza);
		
		return listaAlarma;
	}
	
	public static List<Tvrtka> dohvatiTvrtku() throws SQLException, IOException {
		
		Connection veza = spajanjeNaBazuPodataka();
		
		Statement statementTvrtka = veza.createStatement();
		ResultSet resultSetTvrtka = statementTvrtka.executeQuery("SELECT * FROM CRM.TVRTKA");
		List<Tvrtka> listaTvrtka = new ArrayList<>();
		
		while (resultSetTvrtka.next()) {
			
			Integer tvrtkaId = resultSetTvrtka.getInt("ID");
			String naziv = resultSetTvrtka.getString("NAZIV");
			String oib = resultSetTvrtka.getString("OIB");
			
			Tvrtka zaposlenik = new Tvrtka(naziv, oib);
			zaposlenik.setId(tvrtkaId);
			
			listaTvrtka.add(zaposlenik);
		}
		
		zatvaranjeVezeNaBazuPodataka(veza);
		
		return listaTvrtka;
	}
	
	public static List<Komunikacija> dohvatiKomunikacije() throws SQLException, IOException {
		
		Connection veza = spajanjeNaBazuPodataka();
		
		Statement statementKomunikacije = veza.createStatement();
		Statement statementKlijenti = veza.createStatement();
		Statement statementZaposlenici = veza.createStatement();
		Statement statementVrstaKomunikacije = veza.createStatement();
		ResultSet resultSetKomunikacije = statementKomunikacije.executeQuery("SELECT * FROM CRM.KOMUNIKACIJA");
		List<Komunikacija> listaKomunikacija = new ArrayList<>();
		
		while (resultSetKomunikacije.next()) {
			
			Integer komunikacijaId = resultSetKomunikacije.getInt("ID");
			String klijentString = resultSetKomunikacije.getString("KLIJENT_ID");
			
			ResultSet resultSetKlijent = statementKlijenti.executeQuery("SELECT * FROM CRM.KLIJENT WHERE ID IS " + klijentString);
			String ime = null;
			String prezime = null;
			String oib = null;
			String brojTelefona = null;
			String eMailAdresa = null;
			LocalDate datumRodjenja = null;
			if (resultSetKlijent.next()) { 
				ime = resultSetKlijent.getString("IME");
				prezime = resultSetKlijent.getString("PREZIME");
				oib = resultSetKlijent.getString("OIB");
				brojTelefona = resultSetKlijent.getString("TELEFON");
				eMailAdresa = resultSetKlijent.getString("EMAIL");
				datumRodjenja = resultSetKlijent.getDate("DATUM_RODJENJA").toLocalDate();
				}
			Klijent klijent = new Klijent(ime, prezime, oib, brojTelefona, eMailAdresa, datumRodjenja);
			
			String zaposlenikString = resultSetKomunikacije.getString("ZAPOSLENIK_ID");
	
			ResultSet resultSetZaposlenik = statementZaposlenici.executeQuery("SELECT * FROM CRM.ZAPOSLENIK WHERE ID IS " + zaposlenikString);
			String imeZ = null;
			String prezimeZ = null;
			String korisnickoIme = null;
			String sifraZaposlenika = null;
			if (resultSetZaposlenik.next()) { 
				imeZ = resultSetZaposlenik.getString("IME");
				prezimeZ = resultSetZaposlenik.getString("PREZIME");
				korisnickoIme = resultSetZaposlenik.getString("KORISNICKO_IME");
				sifraZaposlenika = resultSetZaposlenik.getString("SIFRA");
				}
			Zaposlenik zaposlenik = new Zaposlenik(imeZ, prezimeZ, korisnickoIme, sifraZaposlenika);
			
			String vrstaKomunikacijeStr = resultSetKomunikacije.getString("VRSTA_KOMUNIKACIJE_ID");
			
			ResultSet resultSetVrKom = statementVrstaKomunikacije.executeQuery("SELECT * FROM CRM.VRSTA_KOMUNIKACIJE WHERE ID IS " + vrstaKomunikacijeStr);
			String naziv = null;
			if (resultSetVrKom.next()) {
				naziv = resultSetVrKom.getString("NAZIV");
			}
			VrstaKomunikacije vrstaKomunikacije = VrstaKomunikacije.valueOf(naziv);
			
			String sadrzajKomunikacije = resultSetKomunikacije.getString("SADRZAJ");
			LocalDateTime vrijemeKomunikacije = resultSetKomunikacije.getTimestamp("VRIJEME").toLocalDateTime();		
			
			Komunikacija komunikacija = new Komunikacija(klijent, zaposlenik, vrstaKomunikacije, sadrzajKomunikacije, vrijemeKomunikacije);
			komunikacija.setId(komunikacijaId);
			
			listaKomunikacija.add(komunikacija);
		}
		
		zatvaranjeVezeNaBazuPodataka(veza);
		
		return listaKomunikacija;
	}
	
	public static List<Usluga> dohvatiUsluge() throws SQLException, IOException {
		
		Connection veza = spajanjeNaBazuPodataka();
		
		Statement statementUsluge = veza.createStatement();
		Statement statementKlijenti = veza.createStatement();
		Statement statementVrstaUsluge = veza.createStatement();
		ResultSet resultSetUsluge = statementUsluge.executeQuery("SELECT * FROM CRM.USLUGA");
		List<Usluga> listaUsluga = new ArrayList<>();
		
		while (resultSetUsluge.next()) {
			
			Integer uslugaId = resultSetUsluge.getInt("ID");
			String klijentString = resultSetUsluge.getString("KLIJENT_ID");
			
			ResultSet resultSetKlijent = statementKlijenti.executeQuery("SELECT * FROM CRM.KLIJENT WHERE ID IS " + klijentString);
			String ime = null;
			String prezime = null;
			String oib = null;
			String brojTelefona = null;
			String eMailAdresa = null;
			LocalDate datumRodjenja = null;
			if (resultSetKlijent.next()) { 
				ime = resultSetKlijent.getString("IME");
				prezime = resultSetKlijent.getString("PREZIME");
				oib = resultSetKlijent.getString("OIB");
				brojTelefona = resultSetKlijent.getString("TELEFON");
				eMailAdresa = resultSetKlijent.getString("EMAIL");
				datumRodjenja = resultSetKlijent.getDate("DATUM_RODJENJA").toLocalDate();
				}
			Klijent klijent = new Klijent(ime, prezime, oib, brojTelefona, eMailAdresa, datumRodjenja);
						
			String vrstaUslugeStr = resultSetUsluge.getString("VRSTA_USLUGE_ID");
			
			ResultSet resultSetVrUsl = statementVrstaUsluge.executeQuery("SELECT * FROM CRM.VRSTA_USLUGE WHERE ID IS " + vrstaUslugeStr);
			String naziv = null;
			if (resultSetVrUsl.next()) {
				naziv = resultSetVrUsl.getString("NAZIV");
			}
			VrstaUsluge vrstaUsluge = VrstaUsluge.valueOf(naziv);
			
			String opisUsluge = resultSetUsluge.getString("OPIS");
			LocalDate datumUsluge = resultSetUsluge.getDate("DATUM").toLocalDate();
			BigDecimal cijenaUsluge = resultSetUsluge.getBigDecimal("CIJENA");
			Boolean uslugaObvaljena = resultSetUsluge.getBoolean("OBAVLJENA");
			Boolean uslugaNaplacena = resultSetUsluge.getBoolean("PLACENA");
			
			Usluga usluga = new Usluga(klijent, vrstaUsluge, opisUsluge, datumUsluge, cijenaUsluge, uslugaObvaljena, uslugaNaplacena);
			usluga.setId(uslugaId);
			
			listaUsluga.add(usluga);
		}
		
		zatvaranjeVezeNaBazuPodataka(veza);
		
		return listaUsluga;
	}
}
