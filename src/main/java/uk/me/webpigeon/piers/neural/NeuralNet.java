package uk.me.webpigeon.piers.neural;

import java.util.ArrayList;

import uk.me.webpigeon.world.Entity;

/**
 * Neural net to act as the brain for things
 * Created by Piers on 10/03/2015.
 */
public class NeuralNet {
    // Number of inputs for the net
    int numberOfInputs;
    // Number of outputs for the net
    int numberOfOutputs;
    // Number of layers - should be greater than 2 (Input and output layer)
    int numberOfLayers;
    // Number of neurons to place in each hidden layer
    int neuronsPerHiddenLayer;
    ArrayList<NeuronLayer> layers = new ArrayList<>();

    public NeuralNet(int numberOfInputs, int numberOfOutputs, int numberOfLayers, int neuronsPerHiddenLayer) {
        this.numberOfInputs = numberOfInputs;
        this.numberOfOutputs = numberOfOutputs;
        this.numberOfLayers = numberOfLayers;
        this.neuronsPerHiddenLayer = neuronsPerHiddenLayer;
    }

    public void createNet() {

        // input layer
        layers.add(new NeuronLayer(neuronsPerHiddenLayer, numberOfInputs));
        // hidden layers
        for (int i = 1; i < numberOfLayers - 1; i++) {
            layers.add(new NeuronLayer(neuronsPerHiddenLayer, neuronsPerHiddenLayer));
        }
        // add output layer
        layers.add(new NeuronLayer(numberOfOutputs, neuronsPerHiddenLayer));
    }

    public ArrayList<Double> getWeights() {
        ArrayList<Double> weights = new ArrayList<>();
        for (NeuronLayer layer : layers) {
            for (Neuron neuron : layer.neurons) {
                for (int i = 0; i < neuron.numberOfInputs; i++) {
                    weights.add(neuron.weights.get(i));
                }
            }
        }
        return weights;
    }

    public void setWeights(ArrayList<Double> weights) {
        int currentWeight = 0;
        for (NeuronLayer layer : layers) {
            for (Neuron neuron : layer.neurons) {
                neuron.weights.clear();
                for (int i = 0; i < neuron.numberOfInputs; i++) {
                    neuron.weights.add(weights.get(currentWeight));
                    currentWeight++;
                }
            }
        }
    }

    public int getNumberOfWeights() {
        int weights = 0;
        for (NeuronLayer layer : layers) {
            for (Neuron neuron : layer.neurons) {
                // Account for its threshold in here
                weights += neuron.weights.size() - 1;
            }
        }
        return weights;
    }

    public ArrayList<Double> getOutputs(ArrayList<Double> initialInputs) {
        ArrayList<Double> outputs = new ArrayList<>();
        ArrayList<Double> inputs = new ArrayList<>();
        int currentWeight;

        if (initialInputs.size() != numberOfInputs) {
            return new ArrayList<>();
        }

        for (int i = 0; i < numberOfLayers; i++) {
            // For every run but the first, set the old outputs as the inputs to the calculation
            if (i > 0) {
                inputs.clear();
                inputs.addAll(outputs);
            } else {
                inputs.addAll(initialInputs);
            }
            // clear the outputs ready for storage
            outputs.clear();
            currentWeight = 0;

//            System.out.println("Layer " + i + " started - input size: " + inputs.size());

            for (Neuron neuron : layers.get(i).neurons) {
                double totalInputs = 0;
                int numInputs = neuron.numberOfInputs;
//                System.out.println("neuron inputs: " + numInputs);
                // For each weight - remembering the last is the weight of the node
                for (int k = 0; k < numInputs; k++) {
//                    System.out.println("K: " + k + " W: " + currentWeight);
                    totalInputs += neuron.weights.get(k);
                    totalInputs *= inputs.get(currentWeight);
                    currentWeight++;
                }

                // Add in the final weight for the whole neuron
                totalInputs += neuron.weights.get(numInputs - 1) * -1;
                outputs.add(calculateSigmoid(totalInputs, 1));
                currentWeight = 0;
            }
        }
        return outputs;
    }

    private double calculateSigmoid(double activation, double response) {
        return (1 / (1 + Math.exp(-activation / response)));
    }

}

/**
 * Individual Neuron
 */
class Neuron {

    // List of weights - last weight is the threshold for the item
    ArrayList<Double> weights;

    // Number of inputs in this neuron
    int numberOfInputs;

    public Neuron(int numberOfInputs) {
        this.numberOfInputs = numberOfInputs;
        weights = new ArrayList<>(numberOfInputs + 1);
        for (int i = 0; i < numberOfInputs + 1; i++) {
            weights.add((Math.random() * 2) - 1);
        }
    }

}

/**
 * Individual Neuron Layer
 */
class NeuronLayer {
    // Number of neurons in this layer
    int numberOfNeurons;

    // The neurons in this layer
    ArrayList<Neuron> neurons = new ArrayList<>();

    public NeuronLayer(int numberOfNeurons, int numberOfInputsPerNeuron) {
        this.numberOfNeurons = numberOfNeurons;
        for (int i = 0; i < numberOfNeurons; i++) {
            neurons.add(new Neuron(numberOfInputsPerNeuron));
        }
    }

}

