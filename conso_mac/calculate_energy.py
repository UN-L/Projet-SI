import re

def extract_power_values(filename):
    power_values = []
    timestamps = []

    power_regex = re.compile(r'Intel energy model derived package power \(CPUs\+GT\+SA\): (\d+\.\d+)W')
    timestamp_regex = re.compile(r'\*\*\* Sampled system activity \((.+?)\) \((\d+\.\d+)ms elapsed\) \*\*\*')

    with open(filename, 'r') as file:
        for line in file:
            power_match = power_regex.search(line)
            timestamp_match = timestamp_regex.search(line)
            
            if power_match:
                power_value = float(power_match.group(1))
                power_values.append(power_value)
            if timestamp_match:
                timestamps.append(float(timestamp_match.group(2)))

    return power_values, timestamps

def calculate_total_energy(power_values, timestamps):
    total_energy = 0.0
    for i in range(1, len(power_values)):
        interval = (timestamps[i] - timestamps[i-1]) / 1000.0  # Convert ms to seconds
        energy = power_values[i-1] * interval
        total_energy += energy
    return total_energy

filename = 'powermetrics_log.txt'

power_values, timestamps = extract_power_values(filename)
total_energy = calculate_total_energy(power_values, timestamps)

print(f"Valeurs de puissance extraites : {power_values}")
print(f"Consommation totale d'énergie : {total_energy:.2f} joules")
