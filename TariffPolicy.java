/**
 * Determines the outcome of a trade request based on tariff policies.
 * 
 * @param proposedTariff  The tariff rate proposed by the trader (%).
 * @param minimumTariff   The minimum acceptable tariff rate (%).
 * @return A String describing the outcome: 
 *         - "Accepted" if proposedTariff >= minimumTariff,
 *         - "Conditionally Accepted" if within 20% lower than minimumTariff,
 *         - "Rejected" otherwise.
 */
public interface TariffPolicy 
{
	String evaluateTrade(double proposedTariff, double minimumTariff);
	
}
