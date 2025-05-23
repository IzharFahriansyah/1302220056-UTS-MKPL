package lib;

public class TaxData {
    private int monthlySalary;
    private int otherMonthlyIncome;
    private int numberOfMonthWorking;
    private int deductible;
    private boolean isMarried;
    private int numberOfChildren;

    // Constructor
    public TaxData(int monthlySalary, int otherMonthlyIncome, int numberOfMonthWorking, int deductible, boolean isMarried, int numberOfChildren) {
        this.monthlySalary = monthlySalary;
        this.otherMonthlyIncome = otherMonthlyIncome;
        this.numberOfMonthWorking = numberOfMonthWorking;
        this.deductible = deductible;
        this.isMarried = isMarried;
        this.numberOfChildren = numberOfChildren;
    }

    // Getters
    public int getMonthlySalary() {
        return monthlySalary;
    }

    public int getOtherMonthlyIncome() {
        return otherMonthlyIncome;
    }

    public int getNumberOfMonthWorking() {
        return numberOfMonthWorking;
    }

    public int getDeductible() {
        return deductible;
    }

    public boolean isMarried() {
        return isMarried;
    }

    public int getNumberOfChildren() {
        return numberOfChildren;
    }
}

public class TaxFunction {
	/**
	 * Fungsi untuk menghitung jumlah pajak penghasilan pegawai yang harus dibayarkan setahun.
	 * 
	 * Pajak dihitung sebagai 5% dari penghasilan bersih tahunan (gaji dan pemasukan bulanan lainnya dikalikan jumlah bulan bekerja dikurangi pemotongan) dikurangi penghasilan tidak kena pajak.
	 * 
	 * Jika pegawai belum menikah dan belum punya anak maka penghasilan tidak kena pajaknya adalah Rp 54.000.000.
	 * Jika pegawai sudah menikah maka penghasilan tidak kena pajaknya ditambah sebesar Rp 4.500.000.
	 * Jika pegawai sudah memiliki anak maka penghasilan tidak kena pajaknya ditambah sebesar Rp 4.500.000 per anak sampai anak ketiga.
	 * 
	 */
	private static final int BASIC_NON_TAXABLE_INCOME = 54000000;
	private static final int MARRIAGE_ALLOWANCE = 4500000;
	private static final int CHILD_ALLOWANCE = 1500000;
	private static final double TAX_RATE = 0.05;
	
	// Menambahkan helper method untuk menghitung penghasilan tidak kena pajak
	private static int calculateNonTaxableIncome(boolean isMarried, int numberOfChildren) {
    	int nonTaxableIncome = BASIC_NON_TAXABLE_INCOME;
    	if (isMarried) {
        	nonTaxableIncome += MARRIAGE_ALLOWANCE;
    	}
    	nonTaxableIncome += Math.min(numberOfChildren, 3) * CHILD_ALLOWANCE;
    	return nonTaxableIncome;
	}

	// Meambahkan helper method untuk validasi input
	private static void validateInputs(int numberOfMonthWorking, int numberOfChildren) {
    	if (numberOfMonthWorking > 12) {
        	throw new IllegalArgumentException("Number of months worked cannot exceed 12.");
    	}
    	if (numberOfChildren < 0) {
        	throw new IllegalArgumentException("Number of children cannot be negative.");
    	}
	}

	public static int calculateTax(TaxData taxData) {
    	validateInputs(taxData.getNumberOfMonthWorking(), taxData.getNumberOfChildren());

    	int nonTaxableIncome = calculateNonTaxableIncome(taxData.isMarried(), taxData.getNumberOfChildren());
    	int taxableIncome = ((taxData.getMonthlySalary() + taxData.getOtherMonthlyIncome()) * taxData.getNumberOfMonthWorking()) - taxData.getDeductible() - nonTaxableIncome;

    	int tax = (int) Math.round(TAX_RATE * taxableIncome);

   		return Math.max(tax, 0); // Pastikan pajak tidak negatif
}
}
